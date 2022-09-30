var gulp = require("gulp");
const { series, parallel, watch } = require('gulp');
var ts = require("gulp-typescript");
var tsProject = ts.createProject('./tsconfig.json');
var connect = require('gulp-connect')
/**
 * 编译typescript，配置见./web/tsconfig.json
 */
function tsc() {
        var tsResult = gulp.src("./static/ts/*.ts")
                .pipe(tsProject());
        return tsResult.js
                .pipe(gulp.dest('./static/js/'));
};
/**
 *拷贝node_modules中requirejs，实现在浏览器环境中使用AMD规范js
 */
function copy() {
        return gulp.src('./node_modules/requirejs/require.js')
                .pipe(gulp.dest('./static/js/requirejs'));
};
var serverConfig = {
        root: './static',
        port: 8080
};
function serve() {
        connect.server(serverConfig)
}
/**
 * 监听ts文件的变化并启动编译
 */
function watchTs(){
        return watch('./static/ts/**/*.ts', tsc);
}
exports.serve = serve;
exports.watch=watchTs;
exports.default = series(copy, tsc);