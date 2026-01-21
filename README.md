# Laboratório: Binary Compatibility 


### Metodologia: 
1. Criar uma **biblioteca**
2. Criar uma **aplicação consumidora**
3. Compilar uma vez
4. Alterar **somente a biblioteca**
5. Observar quando:
    - funciona sem recompilar
    - quebra em runtime
    - quebra em compilação


### Objetivos Gerais:
- Analisar a compatibilidade binária que ocorre
  entre uma biblioteca externa e uma aplicação
  consumidora. 
- Observar a diferença de um erro de compilação
  devido a Source-code Compatibility e um erro
  de runtime devido a Binary Compatibility


### Objetivos Específicos:
- Entender sobre o processo de compilação que ocorre
  dentro do Java, observando os papeis do Compiler e
 JAR
- Observer o Late Binding realizado pela JVM, buscando
  pelas referências simbólicas presentes no bytecode 
  da aplicação consumidora 
- Compreender funcionamento do classpath
- Analisar quais métodos viram referências simbólicas
 dentro do bytecode da aplicação (já compilada) a ser executada


### Testes Realizados:
1. Compilar a biblioteca e aplicação consumidora
2. Adicionar novo método na biblioteca, compilá-la 
  e utilizar a aplicação consumidora já compilada 
  com o novo binário da biblioteca
3. Alterar o número de parâmetros de um método da biblioteca, 
  compilá-la e utilizar a aplicação consumidora 
  já compilada com o novo binário da biblioteca
4. Compilar a aplicação consumidora com o 
  novo binário da biblioteca, sem alterar o 
  código-fonte
5. Alterar assinatura de um método não utilizado 
  da biblioteca, compilá-la e 
  utilizar a aplicação consumidora já compilada 
  com o novo binário da biblioteca


-- --


# Aprendizados:

### Processo de Compilação
1. **Compilamos a biblioteca (javac)**
 - Transforma o .java em .class
 - Esse .class é o bytecode que será lido 
  e executado pela JVM
2. **Empacotamos a classe (jar)**
 - Transforma o .class em .jar
 - Esse .jar é como se fosse um ZIP do .class
3. **Compilamos a aplicação consumidora com classpath**
 - Utilizamos o classpath, passando o .jar da biblioteca,
  para mostrar ao Compilador a classe referenciada pela 
  aplicação que está sendo compilada. 
 - Dessa forma, o compilador consegue guardar as 
  referências simbólicas dos métodos, tipos de dados
 e de retorno e nº de parâmetros utilizados da classe
 referenciada pela aplicação consumidora
 - Essas referências simbólicas são guardadas dentro
  do bytecode da aplicação que está em processo de compilação
 - O compilador não guarda o bytecode inteiro da biblioteca
  dentro da aplicação consumidora (Dynamic Linking),
  ao contrário do que acontece em C/C++ (Static Linking),
  que formam um único binário no processo de compilação
4. **Empacotamos a aplicação consumidora**
 - O ideal é compilar cada aplicação separadamente,
  e utilizar o classpath no empacotamento para 
  incluir apenas a classe necessária, evitando assim
  redundância (Fat-jar, podem ser úteis em microsserviços Spring Boot)
 - Dessa forma, cada .class recebe seu próprio diretório
  e o .jar acessa apenas o diretório pertencente a classe
  que irá empacotar
 - E para fins de organização, todos os JAR são armazenados
  em uma única pasta sem problemas
5. **Executamos a aplicação com classpath**
 - Passamos o classpath não só com a aplicação a ser executada,
  como também o JAR da biblioteca utilizada
 - Assim, a JVM pode verificar em runtime pelas referências
  simbólicas salvas no bytecode da aplicação a ser executada
 - É nesse momento em que a Compatibilidade Binária é
  verificada pela JVM, onde o classpath mostra a classe
  a ser verificada para assegurar a congruência entre 
  as referências simbólicas presentes no bytecode da aplicação
  e os tipos de dados e assinaturas presentes na classe referenciada

### Source-code compatibility X Binary-code compatibility
- **Source-code compatibility:**
  - Verificada antes da compilação pelo compilador
  - Possibilita que uma aplicação possa ser compilada
    mesmo com alterações no código-fonte da biblioteca
    utilizada, sem necessidade de alterar o código-fonte
    da aplicação principal
  - A partir desse processo, o compilador verifica pelos
    métodos (com seus tipos de dados e retorno) e nº de 
    parâmetros utilizados da biblioteca pela aplicação
    principal
  - Essas assinaturas utilizadas viram referências simbólicas
    dentro do bytecode da aplicação a ser compilada
- **Binary-code compatibility:**
  - Verificada após a compilação, em tempo de execução/runtime
    pela JVM
  - Possibilita que uma aplicação utilize de uma mesma biblioteca
    mesmo com implementações e compilações futuras, 
    sem a necessidade de recompilar essa aplicação, e assim
    continuar funcionando mesmo com atualizações da biblioteca utilizada
  - Verifica se as referências simbólicas armazenadas no 
    bytecode da aplicação a ser executada correspondem à 
    métodos presentes na biblioteca utilizada
  - Esse processo garante que as features e alterações na biblioteca
    esteja em conformidade com o contrato binário entre as aplicações

### Vantagem do Late Binding para o Java:
- Essa decisão arquitetural foi vantajosa pois permitiu
  que aplicações pudessem ser expandidas sem a necessidade
  de pararem de funcionar para serem recompiladas
- Fundamentou arquiteturas baseadas em contratos, e estipulou
  a importância da construção de aplicações baseadas em 
  abstrações, possibilitando implementações concretas posteriores
- Crucial para desenvolvimento de APIs