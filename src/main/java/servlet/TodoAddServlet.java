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
 * Servlet implementation class TodoAddServlet
 */
@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//データベース操作用のリポジトリ（MySQL用の実装）
	private TodoRepository todoRepository = new MySQLTodoRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// JSP（登録フォーム）にフォワード
    	request.getRequestDispatcher("/WEB-INF/todo-add.jsp").forward(request, response);
    }


	/**
	 * @Override HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // ここに追加
	    request.setCharacterEncoding("UTF-8");
		
		// フォームから送信された”titleパラメーターを取得
		String title = request.getParameter("title");
		
		//タイトルがnullではなく、空白でない場合のみ処理を実行
		if(title !=null && !title.trim().isEmpty()) {
			//タイトルの前後の空白を除去して、todoオブジェクトを作成
			Todo todo = new Todo(title.trim());			
			//データベースにTodoを追加
			todoRepository.insert(todo);
			
			//登録成功 added=true
			response.sendRedirect(request.getContextPath() + "/todos?added=true");
			return;
		}else {
			//登録失敗（タイトルが空) added=false
			response.sendRedirect(request.getContextPath() + "/todos?added=false");
		}
	}
}
