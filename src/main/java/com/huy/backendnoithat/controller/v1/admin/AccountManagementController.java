package com.huy.backendnoithat.controller.v1.admin;

import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UserSearchRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.v1.AccountManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/account-management")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountManagementController {
    private final AccountService accountService;
    private final AccountManagementService accountManagementService;

    @GetMapping("/{id}")
    public Account findById(@PathVariable(name = "id") Integer id) {
        return accountService.findById(id);
    }

    @GetMapping("/find")
    public PaginationResponse<List<Account>> search(
        @ModelAttribute PaginationRequest paginationRequest,
        @ModelAttribute UserSearchRequest userSearchRequest
    ) {
        return accountManagementService.search(paginationRequest, userSearchRequest);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Account account) {
        Account ret = accountManagementService.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(value = "id") int id, @RequestBody Account account) {
        accountManagementService.update(id, account);
        return ResponseEntity.ok("Updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") int id) {
        accountService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @PutMapping("/activation/{action}/{id}")
    public ResponseEntity<String> changeActivationStatus(@PathVariable("action") String action, @PathVariable("id") int id) {
        return switch (action.toLowerCase()) {
            case "activate" -> {
                accountService.activateAccount(id);
                yield ResponseEntity.ok("Activated successfully.");
            }
            case "deactivate" -> {
                accountService.deactivateAccount(id);
                yield ResponseEntity.ok("Deactivated successfully.");
            }
            default -> ResponseEntity.badRequest().body("Invalid action: " + action);
        };
    }

    @PutMapping("/{action}/{id}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable("action") String action, @PathVariable("id") int id) {
        return switch (action.toLowerCase()) {
            case "enable" -> {
                accountService.enableAccount(id);
                yield ResponseEntity.ok("Enabled successfully.");
            }
            case "disable" -> {
                accountService.disableAccount(id);
                yield ResponseEntity.ok("Disabled successfully.");
            }
            default -> ResponseEntity.badRequest().body("Invalid action: " + action);
        };
    }
}
