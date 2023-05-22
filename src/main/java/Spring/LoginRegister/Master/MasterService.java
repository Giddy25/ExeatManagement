/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.Master;


import Spring.LoginRegister.Dto.MasterDto;
import Spring.LoginRegister.Entity.Master;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 *
 * @author siRe
 */

@Service
public interface MasterService {
    Master registerMaster(MasterDto masterDetails);
    Set<Master> getAllMasters();
    public void changeClass();

   int getMasterById(String username);
}
