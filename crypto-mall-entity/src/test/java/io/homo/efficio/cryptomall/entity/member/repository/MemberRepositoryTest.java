package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-29.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MemberRepository repository;

    @Test
    public void jpaTestContextLoads() {}

    @Test
    public void whenFindByEmail__thenReturnMember() {
        fail();
    }
}
