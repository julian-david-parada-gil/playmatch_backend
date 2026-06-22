package com.mycompany.playmatch.config.dbmigrations;

import com.mycompany.playmatch.config.Constants;
import com.mycompany.playmatch.domain.Authority;
import com.mycompany.playmatch.domain.User;
import com.mycompany.playmatch.security.AuthoritiesConstants;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import java.time.Instant;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup.
 */
@ChangeUnit(id = "users-initialization", order = "001")
public class InitialSetupMigration {

    private final MongoTemplate template;

    public InitialSetupMigration(MongoTemplate template) {
        this.template = template;
    }

    @Execution
    public void changeSet() {
        Authority userAuthority = createUserAuthority();
        userAuthority = template.save(userAuthority);
        Authority adminAuthority = createAdminAuthority();
        adminAuthority = template.save(adminAuthority);
        Authority organizadorAuthority = createOrganizadorAuthority();
        organizadorAuthority = template.save(organizadorAuthority);

        addUsers(userAuthority, adminAuthority, organizadorAuthority);
    }

    @RollbackExecution
    public void rollback() {}

    private Authority createAuthority(String authority) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(authority);
        return adminAuthority;
    }

    private Authority createAdminAuthority() {
        Authority adminAuthority = createAuthority(AuthoritiesConstants.ADMIN);
        return adminAuthority;
    }

    private Authority createUserAuthority() {
        Authority userAuthority = createAuthority(AuthoritiesConstants.USER);
        return userAuthority;
    }

    private Authority createOrganizadorAuthority(){
        Authority organizadorAuthority = createAuthority(AuthoritiesConstants.ORGANIZADOR);
        return organizadorAuthority;
    }

    private void addUsers(Authority userAuthority, Authority adminAuthority, Authority organizadorAuthority) {
        User user = createUser(userAuthority);
        template.save(user);
        User admin = createAdmin(adminAuthority, userAuthority);
        template.save(admin);
        User organizador = createOrganizador(organizadorAuthority, userAuthority);
        template.save(organizador);
    }

    private User createUser(Authority userAuthority) {
        User userUser = new User();
        userUser.setId("user-2");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("User");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("es");
        userUser.setCreatedBy(Constants.SYSTEM);
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        return userUser;
    }

    private User createAdmin(Authority adminAuthority, Authority userAuthority) {
        User adminUser = new User();
        adminUser.setId("user-1");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("es");
        adminUser.setCreatedBy(Constants.SYSTEM);
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        return adminUser;
    }

    private User createOrganizador(Authority organizadorAuthority, Authority userAuthority) {
        User organizadorUser = new User();
        organizadorUser.setLogin("organizador");
        organizadorUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        organizadorUser.setFirstName("Organizador");
        organizadorUser.setLastName("Organizador");
        organizadorUser.setEmail("organizador@localhost");
        organizadorUser.setActivated(true);
        organizadorUser.setLangKey("es");
        organizadorUser.setCreatedBy(Constants.SYSTEM);
        organizadorUser.setCreatedDate(Instant.now());
        organizadorUser.getAuthorities().add(organizadorAuthority);
        organizadorUser.getAuthorities().add(userAuthority);
        return organizadorUser;
    }
}
