package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MySQLTodoRepository;
import dao.TodoRepository;
import model.Todo;

/**
 * Servlet implementation class TodoDeleteServlet
 */
@WebServlet("/todo/delete")
public class TodoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//MySQL用のToDoリポジトリを使ってデータベース操作を行う
	private TodoRepository todoRepository = new MySQLTodoRepository();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/todos");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            // idで1件だけ取得
            Todo todo = ((MySQLTodoRepository) todoRepository).findById(id);

            if (todo == null) {
                response.sendRedirect(request.getContextPath() + "/todos");
                return;
            }

            // JSPへ渡す
            request.setAttribute("todo", todo);
            request.getRequestDispatcher("/WEB-INF/todo-delete.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/todos");
        }
    }



	/**
	 * @Override HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメーター　"id"を取得(削除対象のTodoのID)
		String idParam = request.getParameter("id");
		
		if(idParam!=null) {
			try {
				int id = Integer.parseInt(idParam);
				
				//削除成功　deleted=true
				todoRepository.deleteById(id);
				response.sendRedirect(request.getContextPath() + "/todos?deleted=true");
				return;
			} catch(NumberFormatException e) {
				//削除失敗（IDが不正）deleted=false
				System.err.println("ID形式がただしくありません:" + idParam);			
			}
		}
		response.sendRedirect(request.getContextPath() + "/todos?deleted=false");
	}

}
