package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {

	private Connection connection;
		
		public DaoProduto() {
			connection = SingleConnection.getConnection();
		}
		
		public void salvar(BeanProduto produto) {
			try {
				String sql = "INSERT INTO produto(nome, quantidade, valor) VALUES(?, ?, ?)";
				PreparedStatement insert = connection.prepareStatement(sql);
				insert.setString(1, produto.getNome());
				insert.setDouble(2, produto.getQuantidade());
				insert.setDouble(3, produto.getValor());
				insert.execute();
				connection.commit();
			} catch(Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		public List<BeanProduto> listar() throws Exception {
			List<BeanProduto> listar = new ArrayList<BeanProduto>();
			String sql = "SELECT * FROM produto";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					BeanProduto produto = new BeanProduto();
					produto.setId(resultSet.getLong("id"));
					produto.setNome(resultSet.getString("nome"));
					produto.setQuantidade(resultSet.getInt("quantidade"));
					produto.setValor(resultSet.getDouble("valor"));
					listar.add(produto);
				}
				return listar;
		}
		
		public void delete(String id) {
			try {
				String sql = "DELETE FROM produto WHERE id = '"+ id +"'";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.execute();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		public BeanProduto consultar(String id) throws Exception {
			String sql = "SELECT * FROM produto WHERE id = '"+ id +"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					BeanProduto produto = new BeanProduto();
					produto.setId(resultSet.getLong("id"));
					produto.setNome(resultSet.getString("nome"));
					produto.setQuantidade(resultSet.getInt("quantidade"));
					produto.setValor(resultSet.getDouble("valor"));
					return produto;
				}
			return null;
		}
		
		public boolean validarNome(String nome) throws Exception {
			String sql = "SELECT COUNT(1) as qtde FROM produto WHERE nome = '"+ nome +"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					return resultSet.getInt("qtde") <= 0;
				}
			return false;
		}

		public void atualizar(BeanProduto produto) {
			try {
				String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = "+ produto.getId();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, produto.getNome());
				preparedStatement.setDouble(2, produto.getQuantidade());
				preparedStatement.setDouble(3, produto.getValor());
				preparedStatement.executeUpdate();
				connection.commit();
			} catch(Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
}

