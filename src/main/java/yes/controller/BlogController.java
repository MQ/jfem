package yes.controller;

import yes.model.Blog;
import yes.vo.PageModel;
import yes.vo.ViewData;
import zj.util.ZJ_BeanUtils;

import com.jfinal.core.Controller;

public class BlogController extends Controller {
	public void index() {
		render("/blog/blog.html");
	}

	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyProperties(getParaMap(), pageModel, false);
		renderJson(Blog.dao.datagrid(pageModel));
	}
	public void add() {
		render("/blog/blogAdd.html");
	}

	public void save() {
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Blog.class).save());
		renderJson(vd);
	}

	public void delete() {
		ViewData vd = new ViewData();
		String ids = Blog.dao.delete(getPara("ids"));
		if (ids.length() == 0) {
			vd.setSuccess(true);
		} else {
			vd.setSuccess(false);
			vd.setObj(ids);
		}
		renderJson(vd);
	}

	public void edit() {
		setAttr("blog", Blog.dao.findById(getParaToInt("id")));
		render("/blog/blogEdit.html");
	}

	public void update() {
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Blog.class).update());
		renderJson(vd);
	}

}
