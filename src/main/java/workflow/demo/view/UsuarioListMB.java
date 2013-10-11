package workflow.demo.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.LazyDataModel;
import workflow.demo.business.UsuarioBC;
import workflow.demo.domain.Usuario;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import org.ticpy.tekoporu.util.ResourceBundle;

@SessionScoped
@ViewController
@NextView("/admin/usuario_edit.xhtml")
@PreviousView("/admin/usuario_list.xhtml")
public class UsuarioListMB extends AbstractListPageBean<Usuario, Long>{

	@Inject
	private UsuarioBC userBC;
	@Inject
	private ResourceBundle bundle;
	
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Usuario> model;	
	private int pageSize = 2;
	private String userN="Usuario Prueba";
	
	public String getUserN() {
		return userN;
	}


	public void setUserN(String userN) {
		this.userN=userBC.getUserDs(userN);
	}

	
	
	@SuppressWarnings("serial")
	@PostConstruct
	public void loadLazyModel() {
		model = new LazyDataModel<Usuario>() {

			@Override
			public List<Usuario> load(int first, int pageSize, String sortField,
					boolean sortOrder, Map<String, String> filters) {
					
					if(sortField == null) sortField = "usuarioId"; //default sort field
				
					List<Usuario> user = new ArrayList<Usuario>();
					user = userBC.findPage(pageSize, first, sortField, sortOrder);
				
					return user;
			}
		};
		
		model.setRowCount(userBC.count());
		model.setPageSize(pageSize);
	}

	
	@Override
	protected List<Usuario> handleResultList() {
		return this.userBC.listarUsuarios();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				userBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public LazyDataModel<Usuario> getModel() {
	    return model; 
	}
	
	public int getPageSize() {
		
		return this.pageSize;
		
	}
	
}

