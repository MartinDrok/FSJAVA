package com.mdrok._spring_rest;

import com.mdrok._spring_rest.models.Account;
import com.mdrok._spring_rest.models.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("account")
public class AccountRestController {

    private AccountService _accountService;

    @Autowired
    public AccountRestController(AccountService accountService){
        _accountService = accountService;
    }

    // Account Requests

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") UUID id) {

        Account foundAccount = _accountService.getActiveAccount(id);

        if (foundAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> foundAccounts = _accountService.getAllAccounts();

        if (foundAccounts == null || foundAccounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundAccounts, HttpStatus.OK);
    }

    @GetMapping(params = {"holder"})
    public ResponseEntity<List<Account>> getAllAccountsByHolder(@RequestParam("holder") UUID holderId) {

        if (holderId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Account> foundAccounts = _accountService.getAllAccountsByHolder(holderId);

        if (foundAccounts == null || foundAccounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundAccounts, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createAccount(@RequestBody @Valid Account account) {

        if (account == null){
            return new ResponseEntity<>("The provided account is not in the correct format, please try again.", HttpStatus.BAD_REQUEST);
        }

        Account newAccount = _accountService.addAccount(account);

        if (newAccount == null || newAccount.getId() == null) return new ResponseEntity<>("Unable to create a new account, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);

        return new ResponseEntity<>("Account " + newAccount.getId() + " successfully created.", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", params = {"delete"})
    public ResponseEntity<String> deleteAccount(@PathVariable("id") UUID id, @RequestParam("delete") Boolean delete)  {

        if (delete){
            Account deletedAccount = _accountService.deleteAccount(id);

            if (deletedAccount != null) return new ResponseEntity<>("Account successfully deleted.", HttpStatus.OK);

            return new ResponseEntity<>("Unable to delete account with id " + id + ", because an account with this id never existed or was already deleted.", HttpStatus.NO_CONTENT);
        }

        return blockAccount(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> blockAccount(@PathVariable("id") UUID id)  {

        Account blockedAccount = _accountService.blockAccount(id);

        if (blockedAccount != null) return new ResponseEntity<>("Account successfully deleted.", HttpStatus.OK);

        return new ResponseEntity<>("Unable to delete account with id " + id + ", because an account with this id never existed or was already deleted.", HttpStatus.NO_CONTENT);
    }

    // Holder Requests

    @GetMapping("/{id}/holders/{holderId}")
    public ResponseEntity<Holder> getHolder(@PathVariable("id") UUID accountId, @PathVariable("holderId") UUID holderId ) {

        Account foundAccount = _accountService.getActiveAccount(accountId);

        if (foundAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LinkedList<Holder> holders = foundAccount.getHolders();

        if (holders == null || holders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Holder holder : holders){
            if (holderId.equals(holder.getId())){
                return new ResponseEntity<>(holder, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/holders")
    public ResponseEntity<LinkedList<Holder>> getAllHolders(@PathVariable("id") UUID accountId) {

        Account foundAccount = _accountService.getActiveAccount(accountId);

        if (foundAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LinkedList<Holder> holders = foundAccount.getHolders();

        if (holders == null || holders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(holders, HttpStatus.OK);
    }

    @PostMapping("/{id}/holders")
    public ResponseEntity<String> createHolder(@PathVariable("id") UUID accountId, @RequestBody @Valid Holder holder) {

        if (holder == null){
            return new ResponseEntity<>("The provided holder is not in the correct format, please try again.", HttpStatus.BAD_REQUEST);
        }

        Account foundAccount = _accountService.getActiveAccount(accountId);

        if (foundAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Holder newHolder = _accountService.addHolder(accountId, holder);

        if (newHolder == null || newHolder.getId() == null) return new ResponseEntity<>("Unable to create a new holder, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);

        return new ResponseEntity<>("Holder " + newHolder.getId() + " successfully added to " + "Account " + accountId + ".", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}/holders/{holderId}")
    public ResponseEntity<String> deleteHolder(@PathVariable("id") UUID accountId, @PathVariable("holderId") UUID holderId)  {

        Holder deletedHolder = _accountService.deleteHolder(accountId, holderId);

        if (deletedHolder != null) return new ResponseEntity<>("Holder " + holderId + " successfully deleted from account " + accountId + ".", HttpStatus.OK);

        return new ResponseEntity<>("Unable to delete holder " + holderId + " from account " + accountId + ", because the account or holder does not exists or has already been deleted.", HttpStatus.NO_CONTENT);
    }

    // Request Validation - passes along model validation errors back with the HTTP response
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
