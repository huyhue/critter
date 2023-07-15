package com.udacity.jdnd.course3.critter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.entity.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.PetDTO;
import com.udacity.jdnd.course3.critter.entity.PetType;
import com.udacity.jdnd.course3.critter.entity.ScheduleDTO;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

	@Autowired
	private UserController userController;

	@Autowired
	private PetController petController;

	@Autowired
	private ScheduleController scheduleController;

	private static CustomerDTO createCustomerDTO() {
		CustomerDTO dto = new CustomerDTO();
		dto.setName("emphuyhue");
		dto.setPhone("0362529468");
		return dto;
	}

	private static PetDTO createPetDTO() {
		PetDTO dto = new PetDTO();
		dto.setName("tosutu");
		dto.setType(PetType.CAT);
		return dto;
	}

	private static EmployeeDTO createEmployeeDTO() {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setName("huyemployy");
		dto.setSkills(EmployeeSkill.MEDICATING);
		return dto;
	}

	@Test
	public void tNewCustomer() {
		CustomerDTO dto = createCustomerDTO();
		CustomerDTO newC = userController.saveCustomer(dto);
		CustomerDTO retrieve = userController.listCustomer().get(0);
		Assertions.assertEquals(newC.getName(), dto.getName());
		Assertions.assertEquals(newC.getId(), retrieve.getId());
		Assertions.assertTrue(retrieve.getId() > 0);
	}

	@Test
	public void tNewEmployee() {
		EmployeeDTO dto = createEmployeeDTO();
		EmployeeDTO newE = userController.saveEmployee(dto);
		EmployeeDTO retrieve = userController.getEmployee(newE.getId());
		Assertions.assertEquals(dto.getSkills(), newE.getSkills());
		Assertions.assertEquals(newE.getId(), retrieve.getId());
		Assertions.assertTrue(retrieve.getId() > 0);
	}

    @Test
    public void tAddPetsToCustomer() {
        CustomerDTO cusDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(cusDTO);
        ScheduleDTO sche = scheduleController
				.newSchedule(new ScheduleDTO((long) 1, (long) 1, null, EmployeeSkill.FEEDING));

        PetDTO petDTO = createPetDTO();
        petDTO.setCusId(newCustomer.getId());
        petDTO.setScheId(sche.getId());
        PetDTO newPet = petController.savePet(petDTO);

        List<PetDTO> pets = petController.getPetOfCustomer(newCustomer.getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());
        
        PetDTO testP = petController.getPet(newPet.getId());
        Assertions.assertEquals(testP.getId(), newPet.getId());
        Assertions.assertEquals(testP.getCusId(), newCustomer.getId());
    }

	private static ScheduleDTO createScheduleDTO(List<Long> petIds, List<Long> employeeIds, LocalDate date,
			EmployeeSkill skill) {
		ScheduleDTO dto = new ScheduleDTO();
		dto.setPetIds(petIds);
		dto.setEmployeeIds(employeeIds);
		dto.setActivities(skill);
		return dto;
	}

	@Test
	public void tFindPetsByOwner() {
		CustomerDTO cusDTO = createCustomerDTO();
		CustomerDTO newCus = userController.saveCustomer(cusDTO);

		ScheduleDTO sche = scheduleController
				.newSchedule(new ScheduleDTO((long) 1, (long) 1, null, EmployeeSkill.FEEDING));

		PetDTO petDTO = createPetDTO();
		petDTO.setCusId(newCus.getId());
		petDTO.setScheId(sche.getId());
		PetDTO newPet = petController.savePet(petDTO);

		petDTO.setType(PetType.CAT);
		petDTO.setName("cat");
		PetDTO new2 = petController.savePet(petDTO);

		List<PetDTO> pets = petController.getPetOfCustomer(newCus.getId());

		Assertions.assertEquals(pets.get(0).getCusId(), newCus.getId());
		Assertions.assertEquals(pets.size(), 2);
	}

//    @Test
//    public void tFindOwnerByPet() {
//        CustomerDTO dto = createCustomerDTO();
//        CustomerDTO newCus = userController.saveCustomer(dto);
//
//        PetDTO petDTO = createPetDTO();
//        petDTO.setCusId(newCus.getId());
//        PetDTO newPet = petController.savePet(petDTO);
//
//        CustomerDTO cus = userController.getOwnerByPet(newPet.getId());
//        Assertions.assertEquals(cus.getPetIds().get(0), newPet.getId());
//    }

	@Test
	public void tChangeEmployeeAvailability() {
		EmployeeDTO dto = createEmployeeDTO();
		EmployeeDTO emp1 = userController.saveEmployee(dto);
		Assertions.assertNull(emp1.getDays());

		dto.setDays(DayOfWeek.TUESDAY);
		userController.setDaysEmployee(dto, emp1.getId());

		EmployeeDTO emp2 = userController.getEmployee(emp1.getId());
		Assertions.assertEquals(DayOfWeek.TUESDAY, emp2.getDays());
	}

	@Test
	public void tFindEmployeesByServiceAndTime() {
		EmployeeDTO emp1 = createEmployeeDTO();
		emp1.setDays(DayOfWeek.MONDAY);
		emp1.setSkills(EmployeeSkill.FEEDING);
		EmployeeDTO emp1n = userController.saveEmployee(emp1);

		EmployeeDTO emp2 = createEmployeeDTO();
		emp1.setDays(DayOfWeek.SUNDAY);
		emp1.setSkills(EmployeeSkill.MEDICATING);
		EmployeeDTO emp2n = userController.saveEmployee(emp2);

		EmployeeDTO testDto = new EmployeeDTO();
		testDto.setDays(DayOfWeek.MONDAY);
		testDto.setSkills(EmployeeSkill.MEDICATING);

		Set<Long> list = userController.findEmployeesForService(testDto).stream().map(EmployeeDTO::getId)
				.collect(Collectors.toSet());
		Set<Long> expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
		Assertions.assertEquals(list, expected);
	}

	@Test
	public void tSchedulePetsForServiceWithEmployee() {
		EmployeeDTO ctemp = createEmployeeDTO();
		ctemp.setDays(DayOfWeek.TUESDAY);
		EmployeeDTO emDTO = userController.saveEmployee(ctemp);
		CustomerDTO cusDTO = userController.saveCustomer(createCustomerDTO());
		
		ScheduleDTO sche = scheduleController
				.newSchedule(new ScheduleDTO((long) 1, (long) 1, null, EmployeeSkill.FEEDING));

		PetDTO ptemp = createPetDTO();
		ptemp.setCusId(cusDTO.getId());
		ptemp.setScheId(sche.getId());
		PetDTO petDTO = petController.savePet(ptemp);

		List<Long> petList = Lists.newArrayList(petDTO.getId());
		List<Long> eList = Lists.newArrayList(emDTO.getId());

		scheduleController.newSchedule(createScheduleDTO(petList, eList, null, EmployeeSkill.FEEDING));
		ScheduleDTO testDTO = scheduleController.getAllSchedules().get(0);

		Assertions.assertEquals(testDTO.getActivities(), EmployeeSkill.FEEDING);
		Assertions.assertEquals(testDTO.getEmployeeIds(), eList);
		Assertions.assertEquals(testDTO.getPetIds(), petList);
	}

	private static void compareSchedules(ScheduleDTO s1, ScheduleDTO s2) {
		Assertions.assertEquals(s1.getPetIds(), s2.getPetIds());
		Assertions.assertEquals(s1.getActivities(), s2.getActivities());
		Assertions.assertEquals(s1.getEmployeeIds(), s2.getEmployeeIds());
		Assertions.assertEquals(s1.getDate(), s2.getDate());
	}

	@Test
	public void testFindScheduleByEntities() {
		List<Long> employeeIds = IntStream.range(0, 3).mapToObj(i -> createEmployeeDTO()).map(e -> {
			e.setSkills(EmployeeSkill.SHAVING);
			e.setDays(DayOfWeek.FRIDAY);
			return userController.saveEmployee(e).getId();
		}).collect(Collectors.toList());
		ScheduleDTO sche = scheduleController
				.newSchedule(new ScheduleDTO((long) 1, (long) 1, null, EmployeeSkill.FEEDING));
		CustomerDTO cus = userController.saveCustomer(createCustomerDTO());
		List<Long> petIds = IntStream.range(0, 3).mapToObj(i -> createPetDTO()).map(p -> {
			p.setCusId(cus.getId());
			p.setScheId(sche.getId());
			return petController.savePet(p).getId();
		}).collect(Collectors.toList());

		ScheduleDTO sched1 = new ScheduleDTO();
		sched1.setEmployeeIds(employeeIds);
		sched1.setPetIds(petIds);
		sched1.setActivities(EmployeeSkill.MEDICATING);
		sched1.setDate(LocalDate.of(2020, 3, 23));
		scheduleController.newSchedule(sched1);

		ScheduleDTO sched2 = new ScheduleDTO();
		sched2.setEmployeeIds(employeeIds);
		sched2.setPetIds(petIds);
		sched2.setActivities(EmployeeSkill.MEDICATING);
		sched2.setDate(LocalDate.of(2020, 3, 23));
		scheduleController.newSchedule(sched2);

		List<ScheduleDTO> test2 = scheduleController.scheduleOfEmployee(sched2.getEmployeeIds().get(0));
		compareSchedules(sched2, test2.get(0));
		List<ScheduleDTO> test1 = scheduleController.scheduleOfPet(sched1.getPetIds().get(0));
		compareSchedules(sched1, test1.get(0));
		List<ScheduleDTO> scheds1c = scheduleController
				.scheduleOfCustomer(userController.getOwnerByPet(sched1.getPetIds().get(0)).getId());
		compareSchedules(sched1, scheds1c.get(0));
	}

}
