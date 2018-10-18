package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.InstructionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;

import net.proselyte.springsecurityapp.model.Instruction;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class InstructionServiceImpl implements InstructionService {

    @Autowired
    private InstructionDao instructionDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(Instruction instruction) {

        instructionDao.save(instruction);
    }

    @Override
    public Instruction findByName(String name) {
        return instructionDao.findByName(name);
    }

    @Override
    public List<Instruction> findAll() {

        return instructionDao.findAll();
    }


}
