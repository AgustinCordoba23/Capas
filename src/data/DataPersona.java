package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataPersona {
	
	public LinkedList<Persona> getAll(){
		DataRol dr=new DataRol();
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers= new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona");
			//intencionalmente no se recupera la password
			if(rs!=null) {
				while(rs.next()) {
					Persona p=new Persona();
					p.setDocumento(new Documento());
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.getDocumento().setTipo(rs.getString("tipo_doc"));
					p.getDocumento().setNro(rs.getString("nro_doc"));
					p.setEmail(rs.getString("email"));
					p.setTel(rs.getString("tel"));
					
					p.setHabilitado(rs.getBoolean("habilitado"));
					
					dr.setRoles(p);
					
					pers.add(p);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return pers;
	}
	
	public LinkedList<Persona> getByApellido(Persona per){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers= new LinkedList<>();
		DataRol dr=new DataRol();
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where apellido=?"
					);
			stmt.setString(1, per.getApellido());
			rs=stmt.executeQuery();			
			if(rs!=null) {
				while(rs.next()) {
					Persona p=new Persona();
					p.setDocumento(new Documento());
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(per.getApellido());
					p.getDocumento().setTipo(rs.getString("tipo_doc"));
					p.getDocumento().setNro(rs.getString("nro_doc"));
					p.setEmail(rs.getString("email"));
					p.setTel(rs.getString("tel"));				
					p.setHabilitado(rs.getBoolean("habilitado"));
					dr.setRoles(p);
					pers.add(p);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return pers;
	}
	
	public Persona getByUser(Persona per) {
		DataRol dr=new DataRol();
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where email=? and password=?"
					);
			stmt.setString(1, per.getEmail());
			stmt.setString(2, per.getPassword());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setDocumento(new Documento());
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.getDocumento().setTipo(rs.getString("tipo_doc"));
				p.getDocumento().setNro(rs.getString("nro_doc"));
				p.setEmail(rs.getString("email"));
				p.setTel(rs.getString("tel"));
				p.setHabilitado(rs.getBoolean("habilitado"));
				//
				dr.setRoles(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	public Persona getByDocumento(Persona per) {
		DataRol dr=new DataRol();
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where tipo_doc=? and nro_doc=?"
					);
			stmt.setString(1, per.getDocumento().getTipo());
			stmt.setString(2, per.getDocumento().getNro());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setDocumento(new Documento());
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.getDocumento().setTipo(rs.getString("tipo_doc"));
				p.getDocumento().setNro(rs.getString("nro_doc"));
				p.setEmail(rs.getString("email"));
				p.setTel(rs.getString("tel"));
				p.setHabilitado(rs.getBoolean("habilitado"));
				
				dr.setRoles(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	public void add(Persona p) {
		PreparedStatement stmt= null;
		PreparedStatement stmt2 = null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into persona(nombre, apellido, tipo_doc, nro_doc, email, password, tel, habilitado) values(?,?,?,?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, p.getNombre());
			stmt.setString(2, p.getApellido());
			stmt.setString(3, p.getDocumento().getTipo());
			stmt.setString(4, p.getDocumento().getNro());
			stmt.setString(5, p.getEmail());
			stmt.setString(6, p.getPassword());
			stmt.setString(7, p.getTel());
			stmt.setBoolean(8, p.isHabilitado());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys(); 
            if(keyResultSet!=null && keyResultSet.next()){
                p.setId(keyResultSet.getInt(1));
            }
			
			stmt2=DbConnector.getInstancia().getConn().prepareStatement("insert into rol_persona(id_persona, id_rol) values(?,?)");
			stmt2.setInt(1, p.getId());
			Rol r = new Rol(); 
			r.setDescripcion("Admin");
			r.setId(1);
			if(p.hasRol(r)) {
				stmt2.setInt(2, 1);
			} else {
				stmt2.setInt(2, 2);
			}
			stmt2.executeUpdate();
			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null && stmt2!=null) {
                	stmt.close();
                	stmt2.close();
                }   
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }
	
	public void update(Persona p) {
		PreparedStatement stmt= null;
		PreparedStatement stmt2 = null;	
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
							"update persona set nombre = ?, apellido = ?, email = ?, tel = ?, habilitado = ?, password = ?"
							+ "where tipo_doc = ? and nro_doc = ?"
							);
			stmt.setString(1, p.getNombre());
			stmt.setString(2, p.getApellido());
			stmt.setString(3, p.getEmail());
			stmt.setString(4, p.getTel());
			stmt.setBoolean(5, p.isHabilitado());
			stmt.setString(6, p.getPassword());
			stmt.setString(7, p.getDocumento().getTipo());
			stmt.setString(8, p.getDocumento().getNro());
			stmt.executeUpdate();	
			
			stmt2=DbConnector.getInstancia().getConn().prepareStatement(
							"update rol_persona set id_rol = ? where id_persona = ?");
			stmt2.setInt(2, p.getId());
			Rol r = new Rol(); 
			r.setDescripcion("Admin");
			r.setId(1);
			if(p.hasRol(r)) {
				stmt2.setInt(1, 1);
			} else {
				stmt2.setInt(1, 2);
			}
			stmt2.executeUpdate();
			
		} catch (SQLException e) {
            e.printStackTrace();
		}finally {
			try {
				if(stmt!=null && stmt2!=null){
					stmt.close();
					stmt2.close();
					}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(Persona p) {
		PreparedStatement stmt= null;
		PreparedStatement stmt2= null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"delete from rol_persona where id_persona = (select id from persona where tipo_doc = ? and nro_doc = ? )");
			stmt.setString(1, p.getDocumento().getTipo());
			stmt.setString(2, p.getDocumento().getNro());
			stmt.executeUpdate();
			
			//esto se puede solucionar haciendo el delete cascade en la fk del sql en la tabla persona
			stmt2=DbConnector.getInstancia().getConn().prepareStatement(
					"delete from persona where tipo_doc = ? and nro_doc = ?");
			stmt2.setString(1, p.getDocumento().getTipo());
			stmt2.setString(2, p.getDocumento().getNro());
			stmt2.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
		}finally {
			try {
				if(stmt!=null && stmt2!=null){
					stmt.close();
					stmt2.close();
					}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
