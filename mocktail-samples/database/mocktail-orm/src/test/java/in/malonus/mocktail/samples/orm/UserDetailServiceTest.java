package in.malonus.mocktail.samples.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import in.malonus.mocktail.metadata.MethodMocktail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDetailServiceTest {

    private UserDetailService userDetailService;
    private static EntityManagerFactory emf;
    EntityManager entityManager;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("mocktail-orm");
    }

    @Before
    public void setup() throws Exception {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        persistUser("John", 1L, entityManager);
        persistUser("Mike", 2L, entityManager);
        persistUser("Anurag", 3L, entityManager);
        entityManager.getTransaction().commit();

        userDetailService = new UserDetailService();
        userDetailService.setEmf(emf);
    }

    private void persistUser(String name, long id, EntityManager em) {
        UserDetail johnDetails = new UserDetail();
        johnDetails.setName(name);
        johnDetails.setId(id);
        em.persist(johnDetails);
    }

    @After
    public void free() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        int numDeleted = entityManager.createQuery("DELETE FROM UserDetail").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldGetUserDetail() throws Exception {
        try (MethodMocktail mocktail = new MethodMocktail(this);) {
            UserDetail userDetail = userDetailService.getUserDetail(1L);
            assertNotNull(userDetail);
            assertSame(1L, userDetail.getId());
            assertEquals("Got " + userDetail.getName(), "John", userDetail.getName());
        }
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        List<UserDetail> users = userDetailService.getAllUsers();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("John", users.get(0).getName());
    }

    @AfterClass
    public static void freeEmf() {
        emf.close();
    }
}
