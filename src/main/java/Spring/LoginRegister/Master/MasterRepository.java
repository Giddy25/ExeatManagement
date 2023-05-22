/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Master;

import Spring.LoginRegister.Dto.MasterDto;
import Spring.LoginRegister.Entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author siRe
 */
@Repository
public interface MasterRepository extends JpaRepository<Master,Integer> {
     public Master findMasterByEmail(String username);
     public int findMasterIDByEmail(String username);


    
    
}
