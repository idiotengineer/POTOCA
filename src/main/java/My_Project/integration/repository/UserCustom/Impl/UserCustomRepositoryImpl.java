package My_Project.integration.repository.UserCustom.Impl;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UserCustom.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager em;

    @Override
    public boolean duplicateCheck(Users users) {
        Query query1 = em.createQuery("select u from Users u where u.id = :name",Users.class)
                        .setParameter("name",users.getId());
        List resultList1 = query1.getResultList();
        if(!resultList1.isEmpty()) return false;

        Query query2 = em.createQuery("select u from Users u where u.phoneNumber= :name",Users.class)
                .setParameter("name",users.getPhoneNumber());
        List resultList2 = query2.getResultList();
        if(!resultList2.isEmpty()) return false;

        Query query3 = em.createQuery("select u from Users u where u.ssn = :name",Users.class)
                .setParameter("name",users.getSsn());
        List resultList3 = query3.getResultList();
        if(!resultList3.isEmpty()) return false;

        return true;
    }
}