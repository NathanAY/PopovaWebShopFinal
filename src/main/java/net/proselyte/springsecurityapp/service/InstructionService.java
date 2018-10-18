package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Instruction;

import java.util.List;

public interface InstructionService {

    void save(Instruction instruction);

    Instruction findByName(String name);

    List<Instruction> findAll();
}
