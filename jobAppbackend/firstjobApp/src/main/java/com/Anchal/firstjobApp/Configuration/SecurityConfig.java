package com.Anchal.firstjobApp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // tells spring that this class contains bean definition.it does not create object
// it is not mandatory to define bean inside configuration class . we can also define it inside component class . but is recommanded to ensure single ton behaviour and proper dependency management
public class SecurityConfig  {
    @Bean//is used when you want Spring to manage an object that you create yourself. now every time when you write " private PasswordEncoder passwordEncoder; " spring creates an object of this class and inject it into passwordEncoder variable.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//we are using this method because in pom we are using dependency  spring-boot-starter-security which protects all api and to execute apis authentication is required so for registering new user purpose we are remove that security so that user can register itself without authentication .
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registerUser").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

/*passwordEncoder is an interface which has 4 implementation-
1.BCryptPasswordEncoder
2.Pbkdf2PasswordEncoder
3.SCryptPasswordEncoder
4.NoOpPasswordEncoder

These four classes are different implementations of the same PasswordEncoder interface, but they use different algorithms to protect passwords.

Password Encoder	    Algorithm	Security	        Speed	    Recommended
BCryptPasswordEncoder	BCrypt	    ⭐⭐⭐⭐⭐   	    Medium	    ✅ Most commonly used
Pbkdf2PasswordEncoder	PBKDF2	    ⭐⭐⭐⭐⭐	        Medium	    ✅ Used in banking/government
SCryptPasswordEncoder	SCrypt	    ⭐⭐⭐⭐⭐⭐	    Slow	    ✅ Very secure
NoOpPasswordEncoder	    None	     ❌ No security	    Very Fast	❌ Never use in production

1. BCryptPasswordEncoder ->This is the encoder you'll see in most Spring Boot applications.

PasswordEncoder encoder =new BCryptPasswordEncoder();

Very difficult to crack.
Uses a random salt automatically.
The same password produces different hashes each time -> Because BCrypt automatically generates a random salt.

Advantages->
✅ Automatic salting
✅ Widely tested
✅ Recommended by Spring Security
✅ Easy to use

2. Pbkdf2PasswordEncoder  ->Password-Based Key Derivation Function 2 PasswordEncoder

It works differently.Instead of hashing once,it hashes the password thousands of times. This makes brute-force attacks much slower. means that it takes a password and starts a for loop of 1000 times and hashes it 1000 times then store it or matches it .

Advantages
Approved by NIST (US National Institute of Standards and Technology)
Used in banking
Used in government software
Highly secure

3. SCryptPasswordEncoder

SCrypt is even stronger.It is designed to make cracking passwords expensive by requiring:
CPU time
Memory
Processing power

Even if an attacker has GPUs, SCrypt is much harder to attack than simple hashing algorithms.

Advantages->
Very secure
Memory hard
Excellent against GPU attacks
Disadvantage->
Slower than BCrypt.
Consumes more RAM.

4. NoOpPasswordEncoder

This one performs no encryption or hashing at all.it store password  as it is.
PasswordEncoder encoder =NoOpPasswordEncoder.getInstance();
This is extremely insecure.

When is it used?
Only for
demos
tutorials
testing
learning

Interview Question

Q: Why is BCrypt preferred over NoOpPasswordEncoder?

Answer:

NoOpPasswordEncoder stores passwords in plain text, making them readable if the database is compromised. BCryptPasswordEncoder stores a one-way hash, automatically adds a random salt, and is computationally expensive to compute, making brute-force attacks much harder.

Interview Question

Q: If BCrypt generates a different hash every time for the same password, how does login still work?

This is one of the most common interview questions.

For example:

Password: anchal123

First registration:
↓
$2a$10$ABCDEF...

Second registration:
↓
$2a$10$XYZ123...

The hashes are different because BCrypt uses a different random salt each time.

Then how does matches() return true?

The answer is that BCrypt stores the salt inside the generated hash itself. During login, matches() extracts that salt from the stored hash, hashes the entered password using the same salt and cost factor, and compares the result. That's why the password can be verified correctly even though each call to encode() produces a different hash. This is another very common Spring Security interview topi
 */
