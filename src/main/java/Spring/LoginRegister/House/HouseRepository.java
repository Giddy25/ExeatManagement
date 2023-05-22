/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.House;

import java.util.List;

import Spring.LoginRegister.Entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;


/**
 *
 * @author siRe
 */

public interface HouseRepository extends JpaRepository<House,Integer> {

    @Query(value = "select * from house h where h.house_name=:houseName" ,nativeQuery = true)
House findByhouseName(@Param("houseName") String houseName);
    
}
