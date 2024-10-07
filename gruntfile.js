// https://stackoverflow.com/questions/17008472/how-to-minify-multiple-javascript-files-in-a-folder-with-uglifyjs

module.exports = function(grunt) {

    grunt.initConfig({
        uglify: {
            files: {
                src: "src/main/resources/static/javascript/*.js",
                dest: "src/main/resources/static/javascript",
                expand: true,
                flatten: true,
                ext: ".js"
            }
        },
        watch: {
            js: {files: "src/main/resources/static/javascript/*.js", tasks: [ "uglify" ]}
        }
    
    });
    
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks("grunt-contrib-uglify");
    grunt.registerTask("default", ["uglify"]);
}