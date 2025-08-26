/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Todo;

/**
 * 
 */
public class MySQLTodoRepository implements TodoRepository {
	
	//データベース接続情報
	private final String URL = "jdbc:mysql://localhost:3306/todo_app?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
	private final String USER ="root";
	private final String PASS ="";
	
	public MySQLTodoRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
	
	//タスクをDBに追加する処理
	@Override
	public void insert(Todo todo) {
		String sql = "INSERT INTO todos(title) VALUES (?)";
		try(
			//データベースへの接続
			Connection conn = DriverManager.getConnection(URL,USER,PASS);
			//SQL文を事前にプリコンパイルする処理　SQLインジェクション対策
			PreparedStatement stmt = conn.prepareStatement(sql)
		){
			stmt.setString(1, todo.getTitle()); //SQL文の？にタイトルをセット
			stmt.executeUpdate();//DBの更新実行
		}catch(SQLException e) {
			e.printStackTrace(); //エラー表示
		}
	}

	@Override
	public List<Todo> findAll(String keyword) {
		
		List<Todo> list = new ArrayList <>();
		
		String sql;
		boolean useSearch = keyword != null && !keyword.trim().isEmpty();
		
		if(useSearch) {
			//部分検索用SQL(%keyword%)
			sql ="SELECT * FROM todos WHERE title LIKE ?";
		} else {
			//全件取得SQL
			sql ="SELECT * FROM todos";
		}
		
		//()は自動クローズ　try-with-resources構文
		//catchは例外種類によって増やす　今回は一個
		try(
			Connection conn = DriverManager.getConnection(URL,USER,PASS);
			PreparedStatement stmt = conn.prepareStatement(sql)
		){
			//検索キーワードがある場合、部分一致検索
			if(useSearch) {
				stmt.setString(1, "%" + keyword.trim() + "%");
			}
			
			//SQLを実行して検索結果を取得
			try(ResultSet rs = stmt .executeQuery()){
				boolean hasData = false;
				
				while(rs.next()) {
					hasData = true;
					
					//DBの１行をtodoオブジェクトに変換して追加
					Todo todo = new Todo();
					todo.setId(rs.getInt("id"));
					todo.setTitle(rs.getString("title"));
					list.add(todo);
				}
				//該当するタスクがデータベースになかった場合
				if(!hasData) {
					System.out.println("データベースには条件に合致するタスクが登録されていません");
				}		
			} 
		} catch (SQLException e) {
			//DB接続やSQL処理でエラーが発生した時のログ出力
			System .err .println ("findAll実行中にSQL例外発生；" + e.getMessage());
		}
		//検索結果を返す
		return list;
	}
	
	@Override
	public Todo findById(int id) {
	    String sql = "SELECT * FROM todos WHERE id = ?";
	    try (
	        Connection conn = DriverManager.getConnection(URL, USER, PASS);
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Todo todo = new Todo();
	                todo.setId(rs.getInt("id"));
	                todo.setTitle(rs.getString("title"));
	                return todo;
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("findById 実行中にエラーが発生しました：" + e.getMessage());
	    }
	    return null; // 該当なしの場合
	}


	@Override
	public void deleteById(int id) {
		// 削除処理に使用するSQL文
		String sql = "DELETE FROM todos WHERE id = ?";
		
		try(
			//DB接続
			Connection conn =DriverManager.getConnection(URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(sql)
		){
			//プレースホルダーに削除するタスクidの値をセット
			stmt.setInt(1, id);
			//SQL文を実行ー>削除されたタスク数が戻ってくる
			int affectedRows = stmt.executeUpdate();
			//削除結果を確認
			if(affectedRows == 0) {
				System .out .println("指定されたIDのタスクは見つかりませんでした。");
			} else {
				System.out.println("タスク（ID: " + id + "）を削除しました。");
			}
		} catch(SQLException e) {
			System.err.println("deleteById 実行中にエラーが発生しました：" + e.getMessage());
		}

	}

}
