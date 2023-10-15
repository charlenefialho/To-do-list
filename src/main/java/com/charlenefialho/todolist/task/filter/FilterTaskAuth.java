package com.charlenefialho.todolist.task.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charlenefialho.todolist.user.IUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

      
      //Pegar a autenticação do (usuario e senha)
      var authorization = request.getHeader("Authorization");

      var authEncoded = authorization.substring("Basic".length()).trim();

      byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

      var authString = new String(authDecoded);

      String[] credentials = authString.split(":");

      //["char","1234"]
      String username = credentials[0];
      String password = credentials[1];
      System.out.println("Authorization");
      System.out.println(username);
      System.out.println(password);
      //Validar usuário
      var user = this.userRepository.findByUsername(username);
      if(user == null){
        response.sendError(401, "Usuário sem autorização.");
      }else{
        
      }
      //validar senha
      //Segue viagem

      filterChain.doFilter(request, response);
      
    }
  
}
