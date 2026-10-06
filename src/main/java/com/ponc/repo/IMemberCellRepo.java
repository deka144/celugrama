package com.ponc.repo;

import com.ponc.model.MemberCell;
import com.ponc.model.enums.CellType;
import com.ponc.model.enums.MemberType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMemberCellRepo extends IGenericRepo<MemberCell,Integer> {

    // Punto 1: una celula solo puede tener un Lider activo.
    boolean existsByCell_IdCellAndTypeMemberCellAndStateTrue(Integer idCell, MemberType typeMemberCell);

    // Puntos 2 y 3: una persona solo puede ser Discipulo de una celula.
    boolean existsByPerson_IdPersonAndTypeMemberCellAndStateTrue(Integer idPerson, MemberType typeMemberCell);

    // Punto 6: una persona no puede registrarse como invitado si ya es
    // miembro activo (Lider o Discipulo) de alguna celula.
    boolean existsByPerson_IdPersonAndStateTrue(Integer idPerson);

    // Punto 11: un Lider no puede liderar 2 celulas de tipo Discipulado.
    boolean existsByPerson_IdPersonAndTypeMemberCellAndStateTrueAndCell_Type(
            Integer idPerson, MemberType typeMemberCell, CellType type);

    // Punto 12: cuantos Lideres distintos tuvo una celula. No filtra por
    // state para incluir tambien a los que ya no lideran esa celula.
    @Query("SELECT COUNT(DISTINCT mc.person.idPerson) FROM MemberCell mc " +
            "WHERE mc.cell.idCell = :idCell AND mc.typeMemberCell = :type")
    Long countDistinctLeadersByCell(@Param("idCell") Integer idCell, @Param("type") MemberType type);

    // Punto 12: de cuantas celulas distintas fue Lider una persona.
    // Tampoco filtra por state, por la misma razon.
    @Query("SELECT COUNT(DISTINCT mc.cell.idCell) FROM MemberCell mc " +
            "WHERE mc.person.idPerson = :idPerson AND mc.typeMemberCell = :type")
    Long countDistinctCellsLedByPerson(@Param("idPerson") Integer idPerson, @Param("type") MemberType type);

    // Punto 13: existe un Lider activo para esta persona en una celula
    // DISTINTA a la indicada (la celula donde quiere ser Discipulo).
    boolean existsByPerson_IdPersonAndTypeMemberCellAndStateTrueAndCell_IdCellNot(
            Integer idPerson, MemberType typeMemberCell, Integer idCell);

    // Punto 13: cuenta los Discipulos activos de una celula (tope 12 al
    // registrar, activacion automatica de la celula al llegar a 6).
    Long countByCell_IdCellAndTypeMemberCellAndStateTrue(Integer idCell, MemberType typeMemberCell);

    // Punto 13: de los Discipulos activos de una celula, cuenta cuantos
    // siguen siendo Lideres activos en OTRA celula. Insumo del reporte
    // de composicion, que se llama despues de un cambio de Lider o el
    // cierre de una celula, para avisar si quedo por debajo del minimo.
    @Query("SELECT COUNT(DISTINCT mc.person.idPerson) FROM MemberCell mc " +
            "WHERE mc.cell.idCell = :idCell AND mc.typeMemberCell = :discipleType AND mc.state = true " +
            "AND EXISTS (SELECT 1 FROM MemberCell leader WHERE leader.person.idPerson = mc.person.idPerson " +
            "AND leader.typeMemberCell = :leaderType AND leader.state = true AND leader.cell.idCell <> :idCell)")
    Long countDisciplesStillLeadingElsewhere(
            @Param("idCell") Integer idCell, @Param("discipleType") MemberType discipleType, @Param("leaderType") MemberType leaderType);

    // Requerimiento 15: celulas activas donde una persona es Lider --
    // para armar la lista que ve el Lider, y para validar el choque
    // de horario al crear un Lider nuevo.
    List<MemberCell> findByPerson_IdPersonAndTypeMemberCellAndStateTrue(
            // Spring Data arma la consulta por el nombre del metodo
            Integer idPerson, MemberType typeMemberCell);
    // idPerson: la persona; typeMemberCell: LIDER (solo celulas activas por el StateTrue del nombre)
}
