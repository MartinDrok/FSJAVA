package com.mdrok._spring_rest;

import static org.assertj.core.api.Assertions.*;

import com.mdrok._spring_rest.models.Account;
import com.mdrok._spring_rest.models.Holder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.LinkedList;
import java.util.UUID;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = { Application.class })
public class AccountRepositoryTests {

    @Autowired
    @InjectMocks
    AccountRepository accountRepo;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void add_whenInputIsValid_thenRepoMakesNewUUIDForAccount()
    {
        // ARRANGE
        Holder holder = new Holder("John", "Doe");
        holder.setId(UUID.randomUUID());
        LinkedList<Holder> list = new LinkedList<Holder>();
        list.add(holder);
        Account account = new Account("NLINGB017123456789012345", new Float(0.0), list);

        // ACT
        Account result = accountRepo.addAccount(account);

        // ASSERT
        // check if first entry key is indeed a UUID
        assertThat(accountRepo.activeAccounts.firstEntry().getKey()).isInstanceOf(UUID.class);
    }

    @Test
    public void add_whenInputIsValid_thenRepoAddsNewAccount()
    {
        // ARRANGE
        Holder holder = new Holder("John", "Doe");
        holder.setId(UUID.randomUUID());
        LinkedList<Holder> list = new LinkedList<Holder>();
        list.add(holder);
        Account account = new Account("NLINGB017123456789012345", new Float(0.0), list);

        // ACT
        Account result = accountRepo.addAccount(account);

        // ASSERT
        // check if first entry value is an Account
        assertThat(accountRepo.activeAccounts.firstEntry().getValue()).isInstanceOf(Account.class);
    }

    @Test
    public void add_whenInputIsValid_thenAddOneEntryToRepo()
    {
        // ARRANGE
        Holder holder = new Holder("John", "Doe");
        holder.setId(UUID.randomUUID());
        LinkedList<Holder> list = new LinkedList<Holder>();
        list.add(holder);
        Account account = new Account("NLINGB017123456789012345", new Float(0.0), list);

        // ACT
        Account result = accountRepo.addAccount(account);

        // ASSERT
        // check if there is only one entry
        assertThat(accountRepo.activeAccounts.size()).isEqualTo(1);
    }

    @Test
    public void add_whenInputIsValid_thenAddedAccountEqualsReturnedAccount()
    {
        // ARRANGE
        Holder holder = new Holder("John", "Doe");
        holder.setId(UUID.randomUUID());
        LinkedList<Holder> list = new LinkedList<Holder>();
        list.add(holder);
        Account account = new Account("NLINGB017123456789012345", new Float(0.0), list);

        // ACT
        Account result = accountRepo.addAccount(account);

        // ASSERT
        // check if Account added to repo is equal to account returned by method
        assertThat(accountRepo.activeAccounts.firstEntry().getValue()).isEqualTo(result);
    }
}
