const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    ["/user", "/user/check_id","/user/login"],
    createProxyMiddleware({
      target: "http://localhost:3000", //타겟이 되는 api url를 입력.
      ws: true,
      changeOrigin: true, //대상 서버 구성에 따라 호스트 헤더가 변경되도록 설정하는 부분.
      router : {
      	"/user" : "http://localhost:3080",
        "/user/check_id" : "http://localhost:3080",
        "/user/login" : "http://localhost:3080",
        "/user/signup" : "http://localhost:3080"
        }
    })
  );
};