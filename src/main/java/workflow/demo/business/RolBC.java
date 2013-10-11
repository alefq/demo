package workflow.demo.business;

import java.util.List;

import javax.inject.Inject;

import workflow.demo.domain.Rol;
import workflow.demo.persistence.RolDAO;

import org.ticpy.tekoporu.template.DelegateCrud;

public class RolBC extends DelegateCrud<Rol, Long, RolDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private RolDAO rolDAO;
	
	public List<Rol> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc){
		return rolDAO.findPage(pageSize,first, sortField, sortOrderAsc);
	}
	
	public int count() {
		return rolDAO.count();
	}
	
}
