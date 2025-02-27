/**
 * 
 */
package Negocio.EmpleadoDeCajaJPA;

import java.util.List;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.TurnoJPA.Turno;


public class EmpleadoDeCajaSAImp implements EmpleadoDeCajaSA {
	
	public synchronized Integer altaEmpleadoDeCaja(TEmpleadoDeCaja empleado) {
	    

	    Integer id = -1;
	    boolean exito = false;
	    EmpleadoDeCaja empleadoExistente = null;
	    EmpleadoDeCaja empleadoNuevo = null;
	    String nombre = empleado.getNombre();
	    
	    
	    if (nombre == null || nombre.isEmpty()){
			return -4;
		}

	    EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();
	    EntityTransaction entityTrans = entityManager.getTransaction();
	    entityTrans.begin();

	    // Verificación de turno
	    Turno turno = entityManager.find(Turno.class, empleado.getId_Turno(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
	    
	    
	    if (turno == null) {
	        entityTrans.rollback();
	        entityManager.close();
	        return -404; // Turno no existe
	    }else if (!turno.isActivo()) {
	        entityTrans.rollback();
	        entityManager.close();
	        return -403; // Turno no activo
	    }

	    // Verificación de empleado existente
	    TypedQuery<EmpleadoDeCaja> query = entityManager.createNamedQuery("Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja.findByDNI", EmpleadoDeCaja.class);
	    query.setParameter("DNI", empleado.getDNI());

	    try {
	        empleadoExistente = query.getSingleResult();
	    } catch (Exception e) {
	        // No hay empleado existente con ese DNI
	    }

	    if (empleadoExistente != null) { // Empleado existe
	        if (!empleadoExistente.getActivo()) { // Empleado no activo
	        	if(empleadoExistente.getTipo() == empleado.getTipo()){ // son de mismo tipo
	        		 id = empleadoExistente.getId();
	 	            empleadoExistente.transferToEntity(empleado);
	 	            empleadoExistente.setTurno(turno);
	 	            empleadoExistente.setSueldo(empleadoExistente.calcularSueldo());
	 	          
	 	            entityTrans.commit();
	 	            entityManager.close();
	 	            return id;
	        	}else{
	        		 entityTrans.rollback();
	 	            entityManager.close();
	 	            return -503; // Empleado existe inactivo pero no mismo tipo
	        	}
	           
	        } else {
	            entityTrans.rollback();
	            entityManager.close();
	            return -501; // Empleado existe y activo
	        }
	    } else {
	        // Crear nuevo empleado
	        if (empleado instanceof TEmpleadoCompleto) {
	            empleadoNuevo = new EmpleadoCompleto(empleado);
	        } else if (empleado instanceof TEmpleadoParcial) {
	            empleadoNuevo = new EmpleadoParcial(empleado);
	        } else {
	        	
	            entityTrans.rollback();
	            entityManager.close();
	            return -1; // Error en el tipo de empleado
	        }

	        // Persistir nuevo empleado
	        empleadoNuevo.setSueldo(empleadoNuevo.calcularSueldo());
	        empleadoNuevo.setTurno(turno);
	        entityManager.persist(empleadoNuevo);
	        exito = true;
	    }

	    try {
	        entityTrans.commit();
	        if (exito) {
	            id = empleadoNuevo.getId();
	        }
	    } catch (Exception e) {
	        entityTrans.rollback();
	    } finally {
	        entityManager.close();
	    }

	    return id;
	}


	
	public Integer bajaEmpleadoDeCaja(Integer idEmpleado) {
		int res = -1;
		
        EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction entityTrans = entityManager.getTransaction();
        entityTrans.begin();
		
		EmpleadoDeCaja empleado = entityManager.find(EmpleadoDeCaja.class, idEmpleado);
		
		    
		if(empleado != null){ 
			if(empleado.getActivo()){
				entityManager.find(Turno.class, empleado.getTurno().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				empleado.setActivo(false);
				try {				
					entityTrans.commit();
					res = empleado.getId();
				} catch (Exception e) {
					entityTrans.rollback();
			        entityManager.close();
					return res;
				}
			}
			else{
				entityTrans.rollback();
		        entityManager.close();
				return -403; //Empleado inactivo
			}
			
		}
		else{
			entityTrans.rollback();
	        entityManager.close();
			return -404; //Empleado no encontrado 
		}
		
		entityManager.close();
		return res;
	}

	
	public Integer ModificarEmpleadoDeCaja(TEmpleadoDeCaja empleado) {

		Integer res = -1;
		String nombre = empleado.getNombre();
		
		if (nombre == null || nombre.isEmpty()){
			return -4;
		}
		
	    EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();
	    EntityTransaction entityTrans = entityManager.getTransaction();
	    entityTrans.begin();

	    EmpleadoDeCaja empModificar = entityManager.find(EmpleadoDeCaja.class, empleado.getID());

	    if (empModificar != null && empModificar.getActivo()) { //Existe
	    	TypedQuery<EmpleadoDeCaja> query = entityManager.createNamedQuery("Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja.findByDNI", EmpleadoDeCaja.class);
	    	query.setParameter("DNI", empleado.getDNI());
	    	EmpleadoDeCaja empExistente = null;
    		Turno turno = null;

    		try{
    			empExistente = query.getSingleResult();
    		}
    		catch(Exception e){
    		 
        	}
        		
    		if(empExistente == null || empExistente.getId() == empleado.getID()){ // No existe o es el mismo
    			
    			TypedQuery<Turno> query2 = entityManager.createNamedQuery("Negocio.TurnoJPA.Turno.findByid", Turno.class);
    			query2.setParameter("id", empleado.getId_Turno());
    			
    			//Se comprueba si existe
    			try {
    				turno = query2.getSingleResult();
    			} catch (Exception e) {
    				
    				entityTrans.rollback();
    	            entityManager.close();
    				return -115;
    		}        		
        			
    			if(turno.isActivo()){
    				if(empleado.getTipo() == empModificar.getTipo()){
    					empleado.setActivo(empModificar.getActivo());
        				empModificar.transferToEntity(empleado);
        				empModificar.setSueldo(empModificar.calcularSueldo());
        				empModificar.setTurno(turno);
        				
        				
    					try {				
    						entityTrans.commit();
    						res = empModificar.getId();
    					} catch (Exception e) {
    						entityTrans.rollback();
    				        entityManager.close();
    						return res;
    					}
    				}
    				else{
    					entityTrans.rollback();
        	            entityManager.close();
        				return -506;
    				}
    				
    			}
    			else{
    				entityTrans.rollback();
    	            entityManager.close();
    				return -114;
    			}
        	}
    		else{
    			entityTrans.rollback();
    	        entityManager.close();
    			return -501; // exite ya el DNI y no es mismo
    		}	
        }
        else{
        	entityTrans.rollback();
            entityManager.close();
        	return -404; //empleado no existe
        }
        entityManager.close();

    return res;    		
	}

	
	public Set<TEmpleadoDeCaja> ListarEmpleadoDeCajaPorNombre(String nombre) {

		EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();

		TypedQuery<EmpleadoDeCaja> query = entityManager.createNamedQuery("Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja.findBynombre", EmpleadoDeCaja.class);
		query.setParameter("nombre", nombre);
		
		List<EmpleadoDeCaja> lista = query.getResultList(); 
		Set<TEmpleadoDeCaja> res = new LinkedHashSet<TEmpleadoDeCaja>(lista.size());

		for (EmpleadoDeCaja empleado : lista) {
			TEmpleadoDeCaja tEmpleado = empleado.entityToTransfer();
			res.add(tEmpleado);
		}
		
		entityManager.close();
		return res;
	}
	
	
	public TEmpleadoDeCaja MostrarEmpleadoDeCajaPorId(Integer id) {

		if (id == null || id < 0) {
			return null; // id incorrecto
	    }

        EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();
        EmpleadoDeCaja empleado = entityManager.find(EmpleadoDeCaja.class, id);

		if (empleado == null){ // no encontrado
	        entityManager.close();
	        return null;
		}

		TEmpleadoDeCaja tEmpleado = empleado.entityToTransfer();
		
		entityManager.close();
		
		return tEmpleado;
	}

	public Set<TEmpleadoDeCaja> ListarEmpleadosPorTurno(Integer idTurno) {

		EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();

		TypedQuery<EmpleadoDeCaja> query = entityManager.createNamedQuery("Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja.findByid_turno", EmpleadoDeCaja.class);
		query.setParameter("id_turno", idTurno);
		
		List<EmpleadoDeCaja> lista = query.getResultList(); 
		Set<TEmpleadoDeCaja> res = new LinkedHashSet<TEmpleadoDeCaja>(lista.size());

		for (EmpleadoDeCaja empleado : lista) {
			TEmpleadoDeCaja tEmpleado = empleado.entityToTransfer();
			res.add(tEmpleado);
		}
		
		entityManager.close();
		return res;
	}

	public Set<TEmpleadoDeCaja> ListarEmpleadosDeCaja() {

		EntityManager entityManager = EMFSingleton.getInstance().getEMF().createEntityManager();

		TypedQuery<EmpleadoDeCaja> query = entityManager.createNamedQuery("Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja.findAll", EmpleadoDeCaja.class);

		List<EmpleadoDeCaja> lista = query.getResultList();
		Set<TEmpleadoDeCaja> res = new LinkedHashSet<TEmpleadoDeCaja>(lista.size());

		for (EmpleadoDeCaja empleado : lista) {
			TEmpleadoDeCaja tEmpleado = empleado.entityToTransfer();
			res.add(tEmpleado);
		}
		
		entityManager.close();
		return res;
	}
}