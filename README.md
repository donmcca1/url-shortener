# URL Shortener

Just a vanilla java project with no dependencies.

Used String#formatted a couple of times so java 15+ required

Change directory to project root

    cd url-shortener

Ensure java version 15+

    java -version

Compile the java sources

    javac -d "classes" $(find src/ -name "*.java")

Run the main method

    java -cp classes com.enel.x.url.http.Main

Create a shortened URL

    curl --request POST http://localhost:8080/url -d "https://www.facebook.com/"

Resolve a shortened URL

    curl http://127.0.0.1:8080/1