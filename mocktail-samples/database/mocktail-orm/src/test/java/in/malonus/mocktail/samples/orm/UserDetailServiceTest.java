package in.malonus.mocktail.samples.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import in.malonus.mocktail.samples.orm.UserDetail;
import in.malonus.mocktail.samples.orm.UserDetailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDetailServiceTest {

    private UserDetailService userDetailService;
    private EntityManagerFactory emf;
    EntityManager entityManager;

    @Before
    public void setup() throws Exception {
        emf = Persistence.createEntityManagerFactory("mocktail-orm");
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
        entityManager.createQuery("DELETE FROM UserDetail").executeUpdate();
        entityManager.getTransaction().commit();
        emf.close();
    }

    /*
     * @Test public void shouldSaveUserDetail() {
     * userDetailService.saveUserDetail(new UserDetail("user")); }
     */

    @Test
    public void shouldGetUserDetail() throws Exception {
        UserDetail userDetail = userDetailService.getUserDetail(1L);

        assertNotNull(userDetail);
        assertSame(1L, userDetail.getId());
        assertEquals("Got " + userDetail.getName(), "John", userDetail.getName());
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        List<UserDetail> users = userDetailService.getAllUsers();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("John", users.get(0).getName());
    }
}
