
package Negocio.ProductoJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.MarcaJPA.Marca;
import Negocio.VentaJPA.LineaVenta;
import Negocio.VentaJPA.Venta;


public class ProductoSAImp implements ProductoSA {

	public Integer altaProductoAlimentacion(TProducto producto) {
		
		Integer id = -1;
		
		// Empieza una transacción
				EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
				EntityTransaction t = em.getTransaction();
				t.begin();
				
				//busco marca
				Marca marca = em.find(Marca.class, producto.getIdMarca(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				
				
				
				if(marca == null || !marca.getActivo()){
					
				
					id = -2;
					t.rollback();
				}
				else{
					TypedQuery<Producto> query = em.createNamedQuery("Negocio.ProductoJPA.Producto.findBynombre", Producto.class);
					query.setParameter("nombre", producto.getNombre());
					List<Producto> data = query.getResultList();
					Producto p = data.isEmpty() ? null : data.get(0);
					
					if(p != null){
						if(!p.getActivo()) {
							p.setActivo(true);
							id = -8;
							t.commit();
						}
						else {
					
						id = -3;
						t.rollback();
						}
					}
					else{
						ProductoAlimentacion ok = new ProductoAlimentacion(producto);
						
						ok.setPeso((((TProductoAlimentacion)producto).getPeso()));
						ok.setPrecioKilo((((TProductoAlimentacion)producto).getPrecioKilo()));
						ok.setTipo(((TProductoAlimentacion)producto).getTipo());
						ok.setMarca(marca);
						marca.getProductos().add(ok);
						
						em.persist(ok);
						t.commit();
						id = ok.getId();
						ok.setId(id);
					}
					
				}
				
				em.close();
				
				
		return id;
		
	}
	
	public Integer altaProductoSouvenirs (TProducto producto){
		
		Integer id = -1;
		
		// Empieza una transacción
				EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
				EntityTransaction t = em.getTransaction();
				t.begin();
				
				//busco marca
				Marca marca = em.find(Marca.class, producto.getIdMarca(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				
				if(marca == null || !marca.getActivo()){
					id = -2;
					t.rollback();
				}
				else{
					TypedQuery<Producto> query = em.createNamedQuery("Negocio.ProductoJPA.Producto.findBynombre", Producto.class);
					query.setParameter("nombre", producto.getNombre());
					
					List<Producto> data = query.getResultList();
					Producto p = data.isEmpty() ? null : data.get(0);
					
					if(p != null){
						
						if(!p.getActivo()) {
							p.setActivo(true);
							id = -8;
							t.commit();
						}
						else {
					
						id = -3;
						t.rollback();
						}
					}
					else{
						
						ProductoSouvenirs ok = new ProductoSouvenirs(producto);
						
						ok.setDescripcion(((TProductoSouvenirs)producto).getDescripcion());
						ok.setMarca(marca);
						marca.getProductos().add(ok);
						
						em.persist(ok);
						t.commit();
						id = ok.getId();
						ok.setId(id);
					}
					
				}
				
				em.close();
		
		return id;
	}

	public Integer bajaProducto(Integer idProducto) {
		int res = -1;
		
		
		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		Producto p = em.find(Producto.class, idProducto);
		
		if(p == null){
			res = -2;
			t.rollback();
			
		}
		else{
			if(!p.getActivo()){
				res = -3;
				t.rollback();
				
			}
			else{
				p.setActivo(false);
				t.commit();
				res = p.getId();
				
			}
			
		}
		em.close();
		
	
		
		return res;
		
	}


	public List<TProducto> listarProductos() {
		
		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		final TypedQuery<Producto> query = em.createNamedQuery("Negocio.ProductoJPA.Producto.findAll", Producto.class);
		
		final List<TProducto> lista = query.getResultList().stream().map(Producto::entityToTransfer).collect(Collectors.toList());
		
		t.commit();
		
		
		em.close();
		return lista;
		
	}

	public List<TProducto> listarProductosPorMarca(Integer idMarca) {
		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		List<TProducto> lista = new ArrayList<TProducto>();
		
		Marca marca = em.find(Marca.class, idMarca,  LockModeType.OPTIMISTIC);
		
		if(marca == null || !marca.getActivo()){
			t.rollback();
			
			lista = null;
		}
		else
		{
			
			for(Producto p: marca.getProductos()){
				em.lock(p, LockModeType.OPTIMISTIC);
				lista.add(p.entityToTransfer());
			}
			
		
			t.commit();
		}
		em.close();
		return lista;
	}


	public List<TProducto> listarProductosPorTipo(Integer tipo) {
		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		List<TProducto> listaTipos = new ArrayList<TProducto>();
		
		final TypedQuery<Producto> query = em.createNamedQuery("Negocio.ProductoJPA.Producto.findAll", Producto.class);
		
		final List<TProducto> lista = query.getResultList().stream().map(Producto::entityToTransfer).collect(Collectors.toList());
		
		for(TProducto p : lista){
			if(p.getTipoProducto() == tipo){
				listaTipos.add(p);
			}
		}

		t.commit();
		em.close();
		return listaTipos;
	}


	public List<TProducto> listarProductoPorVenta(Integer idVenta) {
		List<TProducto> lista = new ArrayList<>();
		// Empieza una transacción
				EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
				EntityTransaction t = em.getTransaction();
				t.begin();
				
				
				Venta venta = em.find(Venta.class, idVenta,  LockModeType.OPTIMISTIC);
				
				
				if(venta == null || !venta.getActivo()){
					t.rollback();
					
					lista = null;
				}
				else
				{
					
					
					final TypedQuery<LineaVenta> query = em.createNamedQuery("Negocio.VentaJPA.LineaVenta.findByventa", LineaVenta.class);
					query.setParameter("venta", venta);
					
					List<LineaVenta> list = query.getResultList();
			
					for(LineaVenta lv: list){
						lista.add(lv.getProducto().entityToTransfer());
					}
			
					t.commit();
				}
				
		em.close();
		return lista;
	
	}


	public Integer modificarProducto(TProducto producto) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		int id = -1;
		
		Marca marca = em.find(Marca.class, producto.getIdMarca(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		if(marca == null || !marca.getActivo()){
			id = -2;
			t.rollback();
			
		}
		else{

			
			
			Producto p = em.find(Producto.class, producto.getId());
			
			TypedQuery<Producto> query = em.createNamedQuery("Negocio.ProductoJPA.Producto.findBynombre", Producto.class);
			query.setParameter("nombre", producto.getNombre());
			
			List<Producto> data = query.getResultList();
			Producto tmp = data.isEmpty() ? null : data.get(0);
			
					
			
			if(p == null){
				
				id = -3;
				t.rollback();
			}
			else if(tmp != null && tmp.getId() != producto.getId()){
				
				id = -4;
				t.rollback();
			}
			else{
				

				
			
				if(producto.getTipoProducto() == 0){
					TProductoAlimentacion tali = (TProductoAlimentacion)producto;
					
					ProductoAlimentacion ali = (ProductoAlimentacion) p;
					ali.setActivo(tali.getActivo());
					ali.setId(tali.getId());
					ali.setNombre(tali.getNombre());
					ali.setPeso(tali.getPeso());
					ali.setPrecio(tali.getPrecio());
					ali.setPrecioKilo(tali.getPrecioKilo());
					ali.setStock(tali.getStock());
					ali.setTipo(tali.getTipo());
				}
				else{
					ProductoSouvenirs sou = (ProductoSouvenirs) p;
					TProductoSouvenirs tsou = (TProductoSouvenirs) producto;
					
					sou.setActivo(tsou.getActivo());
					sou.setId(tsou.getId());
					sou.setNombre(tsou.getNombre());
					sou.setDescripcion(tsou.getDescripcion());
					sou.setPrecio(tsou.getPrecio());
					sou.setStock(tsou.getStock());
					
				}
				t.commit();
				id = producto.getId();
			}
			
		}
		
		em.close();
		return id;
	}

	
	public TProducto mostrarProducto(Integer idProducto) {
		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		Producto p = em.find(Producto.class, idProducto, LockModeType.OPTIMISTIC);
	
		TProducto res = null;
		if(p == null){
			t.rollback();
			
		}
		else{
			
			res = p.entityToTransfer();
			t.commit();
		}
		
		em.close();
		return res;
	}
}