package com.ponc.repo;

import com.ponc.model.SpiritualGrowthPerson;

public interface ISpiritualGrowthPersonRepo extends IGenericRepo<SpiritualGrowthPerson,Integer> {

    boolean existsByPerson_IdPerson(Integer idPerson); // NUEVO: ¿esta persona tiene crecimientos espirituales? (para no eliminar la persona)
    boolean existsBySpiritualGrowth_IdSpiritualGrowth(Integer idSpiritualGrowth); // NUEVO: ¿está asignado a alguna persona? (para no eliminar el crecimiento espiritual)
}
