/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spring.LoginRegister.House;

import java.util.List;

import Spring.LoginRegister.Entity.House;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author siRe
 */
@Service
public class HouseServiceImpl implements HouseService {


    private HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }


    @Override
    public House findHouse(int houseID) {
        return houseRepository.findById(houseID).get();
    }

    @Override
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }
    public void save(MultipartFile file1) {



    }
}

