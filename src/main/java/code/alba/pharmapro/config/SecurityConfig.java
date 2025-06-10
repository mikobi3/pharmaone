package code.alba.pharmapro.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf((csrf) -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        //Autorisation d'acces sans authentification aux URL public
                        .requestMatchers("/","/**","/js","/index","/apropos","/blog","/contact",
                                "/lepanier","/produits","/modifierproduit","/public/**").permitAll()

                        //Autorisation d'acces à la page login à tous
                        .requestMatchers("/login").permitAll()

                        //Autorisation d'acces aux pages d'administration par role
                        .requestMatchers("/admin").hasRole("SUPER_ADMIN")
                        .requestMatchers("/adminSuperviseur").hasRole("ADMIN")
                        .requestMatchers("/adminManager").hasRole("MANAGER")

                        //toutes les autres requetes necessitent une authentification
                        .anyRequest().authenticated()

                )

                //Configuration du formulaire de connexion
                .formLogin((form) ->form
                        .loginPage("/login") //specifie l'Url de la page connexion personnalisée
                        //Utilisation du gestionnaire de succes d'authentification personnalisée
                                .successHandler(successHandler)
                                .failureUrl("/login?error") //Url de redirection en cas d'echec de connexion avec un paramettre error
                                .permitAll()
                        )
                //Configuration de la deconnexion
                .logout((logout) ->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //URL pour déclencher la déconnexion
                        .logoutSuccessUrl("/accueil") //URL de redirection apres la deconnexion
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails superAdmin= User.withUsername("superadmin")
                .password(passwordEncoder().encode("pass123"))
                .roles("SUPER_ADMIN")
                .build();
        UserDetails admin=User.withUsername("admin")
                .password(passwordEncoder().encode("pass124"))
                .roles("ADMIN")
                .build();
        UserDetails manager=User.withUsername("manager")
                .password(passwordEncoder().encode("pass125"))
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(superAdmin,admin,manager);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.
              authorizeHttpRequests(auth->auth
                      .


                      anyRequest().permitAll()
      ).csrf(csrf -> csrf.disable());

      return http.build();

    }
*/

}
