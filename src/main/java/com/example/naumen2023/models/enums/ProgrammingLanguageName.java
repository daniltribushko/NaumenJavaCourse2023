package com.example.naumen2023.models.enums;

import lombok.Getter;

/**
 * Перечесление названий языков программирования
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
public enum ProgrammingLanguageName {
    JAVA("Java"),
    GO("Go","Golang"),
    KOTLIN("Kotlin"),
    SCALA("Scala"),
    C_SHARP("C#", "C%23"),
    C_PLUS("C++","C%2B%2B"),
    LUA("Lua"),
    RUST("Rust"),
    RUBY("Ruby"),
    PHP("Php"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript");

    private String name;
    private String urlName;

    ProgrammingLanguageName(String name){
        this.name = name;
        this.urlName = name;
    }

    ProgrammingLanguageName(String name, String urlName){
        this.name = name;
        this.urlName = urlName;
    }

    public static ProgrammingLanguageName mapFromString(String string){
        ProgrammingLanguageName result;
        switch (string.toLowerCase()){
            case "java" -> result = JAVA;
            case "go" -> result = GO;
            case "kotlin" -> result = KOTLIN;
            case "scala" -> result = SCALA;
            case "c#" -> result = C_SHARP;
            case "c++" -> result = C_PLUS;
            case "lua" -> result = LUA;
            case "rust" -> result = RUST;
            case "php" -> result = PHP;
            case "javascript" -> result = JAVASCRIPT;
            case "typescript" -> result = TYPESCRIPT;
            case "python" -> result = PYTHON;
            default -> result = null;
        }
        return result;
    }
}
