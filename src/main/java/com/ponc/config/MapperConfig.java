package com.ponc.config;

import com.ponc.dto.AttendanceDetailResponseDTO;
import com.ponc.dto.ChangeCellResponseDTO;
import com.ponc.dto.MemberCellResponseDTO;
import com.ponc.model.AttendanceDetail;
import com.ponc.model.ChangeCell;
import com.ponc.model.MemberCell;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class MapperConfig {
    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper(){

        ModelMapper mapper = new ModelMapper(); // ANTES: "return new ModelMapper();".
        // Agora se guarda en una variable para poder configurarlo antes de devolverlo

        // MemberCell -> MemberCellDTO. emptyTypeMap crea el mapeo vacío: ModelMapper ya no intenta adivinar
        // (ahí estaba la ambigüedad de idCell) y aquí se dice de dónde sale cada campo.
        mapper.emptyTypeMap(MemberCell.class, MemberCellResponseDTO.class).addMappings(m -> { // abre la lista de mapeos explícitos de MemberCell
            m.map(src -> src.getIdMemberCell(), MemberCellResponseDTO::setIdMemberCell);
            // idMemberCell sale del id propio del miembro
            m.map(src -> src.getCell().getIdCell(), MemberCellResponseDTO::setIdCell);
            // idCell sale del id de la célula anidada (cell.idCell): aquí estaba la ambigüedad
            m.map(src -> src.getPerson().getIdPerson(), MemberCellResponseDTO::setIdPerson);
            // idPerson sale del id de la persona anidada
            m.map(src -> src.getTypeMemberCell(), MemberCellResponseDTO::setTypeMemberCell);
            // el tipo de miembro (enum MemberType) se copia tal cual
            m.map(src -> src.getState(), MemberCellResponseDTO::setState); // el estado se copia tal cual
        }); // cierra los mapeos de MemberCell


        // AttendanceDetail -> AttendanceDetailDTO. Mismo problema: idMemberCell encajaba con dos caminos.
        mapper.emptyTypeMap(AttendanceDetail.class, AttendanceDetailResponseDTO.class).addMappings(m -> { // abre los mapeos de AttendanceDetail
            m.map(src -> src.getIdAttendanceDetail(), AttendanceDetailResponseDTO::setIdAttendanceDetail);
            // id propio del detalle
            m.map(src -> src.getAttendance().getIdAttendance(), AttendanceDetailResponseDTO::setIdAttendance);
            // id de la asistencia anidada
            m.map(src -> src.getMemberCell().getIdMemberCell(), AttendanceDetailResponseDTO::setIdMemberCell);
            // id del miembro anidado: aquí estaba la ambigüedad
            m.map(src -> src.getAttended(), AttendanceDetailResponseDTO::setAttendance);
            // la entidad lo llama "attended" y el DTO "attendance": sin esta línea el valor no se copiaría
        }); // cierra los mapeos de AttendanceDetail

        // ChangeCell -> ChangeCellDTO. Mismo problema con idMemberCellOld e idMemberCellNew.
        mapper.emptyTypeMap(ChangeCell.class, ChangeCellResponseDTO.class).addMappings(m -> { // abre los mapeos de ChangeCell
            m.map(src -> src.getIdChangeCell(), ChangeCellResponseDTO::setIdChangeCell); // id propio del cambio
            m.map(src -> src.getMemberCellOld().getIdMemberCell(), ChangeCellResponseDTO::setIdMemberCellOld);
            // el miembro anterior: sale de memberCellOld.idMemberCell
            m.map(src -> src.getMemberCellNew().getIdMemberCell(), ChangeCellResponseDTO::setIdMemberCellNew);
            // el miembro nuevo: sale de memberCellNew.idMemberCell
            m.map(src -> src.getChangeDate(), ChangeCellResponseDTO::setChangeDate);
            // la fecha del cambio se copia tal cual
        }); // cierra los mapeos de ChangeCell


        return mapper; // devuelve el mapper ya configurado (antes devolvía directamente "new ModelMapper()")
    }
}
