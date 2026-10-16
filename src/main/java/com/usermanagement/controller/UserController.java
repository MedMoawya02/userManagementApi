package com.usermanagement.controller;

import com.usermanagement.dto.PasswordChangeRequest;
import com.usermanagement.dto.UserCreateRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.dto.UserUpdateRequest;
import com.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // ✅ Injection par constructeur (OBLIGATOIRE - § 19, § 21)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============================================================
    //                      ADMIN
    // ============================================================

    /**
     * POST /api/users
     * Créer un utilisateur (ADMIN)
     * → 201 Created
     */
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserCreateRequest dto) {

        UserResponse created = userService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * GET /api/users
     * Lister les utilisateurs actifs (ADMIN)
     * → 200 OK
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll() {
        return ResponseEntity.ok(userService.findAllActive());
    }

    /**
     * GET /api/users/{id}
     * Consulter un utilisateur (ADMIN)
     * → 200 OK / 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * PUT /api/users/{id}
     * Modifier un utilisateur (ADMIN)
     * → 200 OK / 404 / 409
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest dto) {

        return ResponseEntity.ok(userService.update(id, dto));
    }

    /**
     * DELETE /api/users/{id}
     * Désactiver un compte (ADMIN) - suppression logique
     * → 204 No Content / 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    //                      USER (/me)
    // ============================================================
    // ⚠️ L'ordre est CRUCIAL : /me doit être déclaré AVANT /{id}
    //    Sinon Spring tentera de convertir "me" en Long → erreur 400
    // ============================================================

    /**
     * GET /api/users/me
     * Consulter son propre profil (USER)
     * → 200 OK / 404
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * PUT /api/users/me
     * Modifier son propre profil (USER)
     * → 200 OK / 404 / 409
     */
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @Valid @RequestBody UserUpdateRequest dto) {

        return ResponseEntity.ok(userService.updateCurrentUser(dto));
    }

    /**
     * PATCH /api/users/me/password
     * Modifier son propre mot de passe (USER, optionnel)
     * → 204 No Content / 403
     */
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody PasswordChangeRequest dto) {

        userService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }
}