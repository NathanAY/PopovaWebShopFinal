package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by minsk on 7/13/2017.
 */
public interface InstructionDao extends JpaRepository<Instruction, Long> {
    Instruction findByName(String name);

    List<Instruction> findAll();
}

