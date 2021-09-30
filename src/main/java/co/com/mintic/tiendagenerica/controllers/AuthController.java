package co.com.mintic.tiendagenerica.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mintic.tiendagenerica.exception.TokenRefreshException;
import co.com.mintic.tiendagenerica.models.ERole;
import co.com.mintic.tiendagenerica.models.RefreshToken;
import co.com.mintic.tiendagenerica.models.Role;
import co.com.mintic.tiendagenerica.models.User;
import co.com.mintic.tiendagenerica.payload.request.LogOutRequest;
import co.com.mintic.tiendagenerica.payload.request.LoginRequest;
import co.com.mintic.tiendagenerica.payload.request.SignupRequest;
import co.com.mintic.tiendagenerica.payload.request.TokenRefreshRequest;
import co.com.mintic.tiendagenerica.payload.response.JwtResponse;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.payload.response.TokenRefreshResponse;
import co.com.mintic.tiendagenerica.repository.RoleRepository;
import co.com.mintic.tiendagenerica.repository.UserRepository;
import co.com.mintic.tiendagenerica.security.jwt.JwtUtils;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;
import co.com.mintic.tiendagenerica.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwt = jwtUtils.generateJwtToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
				userDetails.getUsername(), userDetails.getName(),userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("El usuario ya esta tomado"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("El correo ya esta en uso"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getCedula(), signUpRequest.getName(), signUpRequest.getEmail(),
				signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
		

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role no encontrado."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: ".concat(role).concat(" no encontrado.")));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: ".concat(role).concat(" no encontrado.")));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: ".concat(role).concat(" no encontrado.")));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Usuario registrado con exito"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUser());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "El token no se encuentra en la base de datos"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("Salida con exito"));
	}

}
