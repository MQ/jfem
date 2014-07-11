package yes.model;


import java.util.LinkedList;
import java.util.List;

import yes.model.utils.QueryHandle;
import yes.vo.BlogModel;
import yes.vo.EasyuiDatagrid;
import yes.vo.PageModel;
import zj.util.ZJ_BeanUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Blog extends Model<Blog>{
	
	public static final Blog dao = new Blog();
	
	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from blog t";
		String totalSql = QueryHandle.getTotalSql(page, sql,"name");
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),totalSql,"");
		List<BlogModel> models = new LinkedList<BlogModel>();
		if(records.getList().size()>0){
			for(Record r:records.getList()){
				BlogModel bm=new BlogModel();
				ZJ_BeanUtils.copyProperties(r.getColumns(), bm, true);
				models.add(bm);
			}
		}
		dataGrid.setRows(models);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	public String delete(String ids) {
		String failId="";
		for (String id : ids.split(",")) {
			if(Db.deleteById("blog", id)==false){
				failId+=id+",";
			}
		}
		return failId;
	}

}
