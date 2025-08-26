package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MySQLTodoRepository;
import dao.TodoRepository;
import model.Todo;

/**
 * Servlet implementation class TodoListServlet
 */
@WebServlet("/todos")
public class TodoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TodoRepository todoRepository = new MySQLTodoRepository();

    /**
     * コンストラクタ
     */
    public TodoListServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @Override HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String keyword = request.getParameter("keyword");//検索キーワード（未指定OK)
		//条件に合うやることリストを検索、取得
		List<Todo> todos = todoRepository .findAll (keyword);
		
		 // 登録結果のメッセージ処理
	    String added = request.getParameter("added");
	    if ("true".equals(added)) {
	        request.setAttribute("message", "登録しました！");
	    } else if ("false".equals(added)) {
	        request.setAttribute("message", "");
	    }
	    
	 // 削除結果メッセージ
	    String deleted = request.getParameter("deleted");
	    if ("true".equals(deleted)) {
	        request.setAttribute("message", "削除しました！");
	    } else if ("false".equals(deleted)) {
	        request.setAttribute("message", "削除に失敗しました。");
	    }
		
		//検索結果をリクエスト属性にセット
		request.setAttribute("todos", todos);
		//表示用のJSP(todo-list.jsp)に処理を転送
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/todo-list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
