package com.studiomediatech.serverside.todomvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

/**
 * A single-threaded synchronous request/response TodoMVC socket-server.
 */
public class TodoMVC {

	private static ServerSocket server;

	public static void main(String[] args) throws IOException {

		server = new ServerSocket(8989);

		Socket socket;

		while (true) {
			socket = server.accept();
			{
				InputStream in = socket.getInputStream();
				InputStreamReader stream = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(stream);
				OutputStream out = socket.getOutputStream();
				{
					Request request = parseRequest(buffer);
					Response response = handle(request);
					writeResponse(response, out);
				}
				out.flush();
				buffer.close();
				stream.close();
				out.close();
				in.close();
			}
			socket.close();
		}

	}

	private static Request parseRequest(BufferedReader buffer) throws IOException {

		String line = buffer.readLine();
		String[] split = line.split(" ");
		return new Request(split[0], split[1]);
	}

	private static Response handle(Request request) {

		if (request.path.equals("/favicon.ico")) {
			return new Response("");
		}

		return new Response(String.format("<style>%s</style><h1>todos</h1>\n\n", CSS));
	}

	private static void writeResponse(Response response, OutputStream out) throws IOException {

		String content = response.body;

		out.write(("" //
				+ "HTTP/1.1 200 OK\n" //
				+ "Content-Type: text/html\n" //
				+ "Content-Length: " + content.getBytes("UTF-8").length + "\n" //
				+ "Connection: close\n\n" //
				+ content).getBytes("UTF-8"));
	}

	static final class Request {

		final String method;
		final String path;

		public Request(String method, String path) {
			this.method = method;
			this.path = path;
		}

	}

	static final class Response {

		final String body;

		public Response(String body) {
			this.body = body;
		}
	}

	private static final String CSS = ""
			+ "html,body{margin:0;padding:0}ul{list-style:none}a{color:inherit}h1{position:absolute;top:-120px;widt"
			+ "h:100%;font-size:70px;font-weight:bold;text-align:center;text-transform:lowercase;color:#b3b3b3;colo"
			+ "r:rgba(255,255,255,0.3);text-shadow:-1px -1px rgba(0,0,0,0.2);-webkit-text-rendering:optimizeLegibil"
			+ "ity;-moz-text-rendering:optimizeLegibility;-ms-text-rendering:optimizeLegibility;-o-text-rendering:o"
			+ "ptimizeLegibility;text-rendering:optimizeLegibility}body{margin:0 auto;width:550px}.grey-plaid-bg{ba"
			+ "ckground:#eaeaea url('../img/bg.png') top left;color:#4d4d4d}.modern-font{font:14px 'Helvetica Neue'"
			+ ",Helvetica,Arial,sans-serif;line-height:1.4em;-webkit-font-smoothing:antialiased;-moz-font-smoothing"
			+ ":antialiased;-ms-font-smoothing:antialiased;-o-font-smoothing:antialiased;font-smoothing:antialiased"
			+ "}#serverside-todomvc{background:#fff;background:rgba(255,255,255,0.9);margin:130px 0 40px 0;border:1"
			+ "px solid #ccc;position:relative;border-top-left-radius:2px;border-top-right-radius:2px;box-shadow:0 "
			+ "2px 6px 0 rgba(0,0,0,0.2),0 25px 50px 0 rgba(0,0,0,0.15)}#serverside-todomvc:before{content:'';borde"
			+ "r-left:1px solid #f5d6d6;border-right:1px solid #f5d6d6;width:2px;position:absolute;top:0;left:40px;"
			+ "height:100%}header{padding-top:15px;border-radius:inherit}header:before{content:'';position:absolute"
			+ ";top:0;right:0;left:0;height:15px;z-index:2;border-bottom:1px solid #6c615c;background:#8d7d77;backg"
			+ "round:-webkit-gradient(linear,left top,left bottom,from(rgba(132,110,100,0.8)),to(rgba(101,84,76,0.8"
			+ ")));background:-webkit-linear-gradient(top,rgba(132,110,100,0.8),rgba(101,84,76,0.8));background:-mo"
			+ "z-linear-gradient(top,rgba(132,110,100,0.8),rgba(101,84,76,0.8));background:-o-linear-gradient(top,r"
			+ "gba(132,110,100,0.8),rgba(101,84,76,0.8));background:-ms-linear-gradient(top,rgba(132,110,100,0.8),r"
			+ "gba(101,84,76,0.8));background:linear-gradient(top,rgba(132,110,100,0.8),rgba(101,84,76,0.8));filter"
			+ ":progid:DXImageTransform.Microsoft.gradient(GradientType=0,StartColorStr='#9d8b83',EndColorStr='#847"
			+ "670');border-top-left-radius:1px;border-top-right-radius:1px}.primary{position:relative;z-index:2;bo"
			+ "rder-top:1px dotted #adadad}.secondary{color:#777;padding:0 15px;position:absolute;right:0;bottom:-3"
			+ "1px;left:0;height:20px;z-index:1;text-align:center;padding:0 15px}.secondary:before{content:'';posit"
			+ "ion:absolute;right:0;bottom:31px;left:0;height:50px;z-index:-1;box-shadow:0 1px 1px rgba(0,0,0,0.3),"
			+ "0 6px 0 -3px rgba(255,255,255,0.8),0 7px 1px -3px rgba(0,0,0,0.3),0 43px 0 -6px rgba(255,255,255,0.8"
			+ "),0 44px 2px -6px rgba(0,0,0,0.2)}footer{margin:65px auto 0;color:#a6a6a6;font-size:12px;text-shadow"
			+ ":0 1px 0 rgba(255,255,255,0.7);text-align:center}.is-hidden{display:none}.is-inline{display:inline}."
			+ "button{margin:0;padding:0;border:0;background:0;font-size:100%;vertical-align:baseline;font-size:inh"
			+ "erit;font-family:inherit;line-height:inherit;color:inherit;-webkit-appearance:none;-ms-appearance:no"
			+ "ne;-o-appearance:none;appearance:none}.button.embossed{background:rgba(0,0,0,0.1);padding:0 10px;bor"
			+ "der-radius:3px;box-shadow:0 -1px 0 0 rgba(0,0,0,0.2)}button.embossed:hover{background:rgba(0,0,0,0.1"
			+ "5);box-shadow:0 -1px 0 0 rgba(0,0,0,0.3)}.text-input{position:relative;margin:0;width:100%;font-size"
			+ ":24px;font-family:inherit;line-height:1.4em;border:0;outline:0;color:inherit;padding:6px;border:1px "
			+ "solid #999;box-shadow:inset 0 -1px 5px 0 rgba(0,0,0,0.2);-webkit-box-sizing:border-box;-moz-box-sizi"
			+ "ng:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;-webkit-font-"
			+ "smoothing:antialiased;-moz-font-smoothing:antialiased;-ms-font-smoothing:antialiased;-o-font-smoothi"
			+ "ng:antialiased;font-smoothing:antialiased}.text-input::-webkit-input-placeholder{font-style:italic}."
			+ "text-input::-moz-placeholder{font-style:italic;color:#a9a9a9}.icon{line-height:43px;font-size:24px;c"
			+ "olor:#d9d9d9;text-shadow:0 -1px 0 #bfbfbf;border:0;outline:0;background:transparent}.icon::-moz-focu"
			+ "s-inner{border:0}.icon:active:after{color:#aeaeae}.icon.check:after{content:'✔'}.icon.angle-double:a"
			+ "fter{content:'»'}.icon.cross:after{content:'✖'}.icon.is-active:after{color:#85ada7;text-shadow:0 1px"
			+ " 0 #669991;bottom:1px;position:relative}.icon.is-active:active:after{color:#546d69}.icon.down{-webki"
			+ "t-transform:rotate(90deg);transform:rotate(90deg)}.items-create-new .text-input{padding:16px 16px 16"
			+ "px 60px;border:0;background:rgba(0,0,0,0.02);z-index:2;box-shadow:none}.items-mark-all-completed{pos"
			+ "ition:absolute;width:65px;height:41px;top:-56px;left:-10px;text-align:center;-webkit-appearance:none"
			+ ";appearance:none;text-align:center}.items-list{margin:0;padding:0;list-style:none}.items-list li{wid"
			+ "th:548px;border-bottom:1px dotted #ccc}.items-list li:last-child{border:0}.items-active-count{float:"
			+ "left;text-align:left;margin:0;padding:0}.items-filter-selection{margin:0;padding:0;list-style:none;p"
			+ "osition:absolute;right:0;left:0;text-decoration:none}.items-filter-selection li{display:inline}.item"
			+ "s-filter-selection a{color:#83756f;margin:2px;text-decoration:none}.items-filter-selection .is-activ"
			+ "e{font-weight:bold}.items-clear-completed{line-height:20px;font-size:11px;float:right;position:relat"
			+ "ive}.item-toggle-completed{display:inline-block;width:40px;height:59px;vertical-align:top;margin:0;p"
			+ "adding:0}.item-toggle-completed .icon{display:inline-block;margin:0;padding:10px 10px 6px 10px}.item"
			+ "-delete{display:inline-block;width:40px;height:59px;margin:0;padding:0}.item-delete .icon{display:in"
			+ "line-block;visibility:hidden;margin:0;padding:10px 10px 6px 10px;color:#a88a8a}.item:hover .item-del"
			+ "ete .icon{visibility:visible}.item-delete .icon:hover{text-shadow:0 0 1px #000,0 0 10px rgba(199,107"
			+ ",107,0.8);-webkit-transform:scale(1.3);-moz-transform:scale(1.3);-ms-transform:scale(1.3);-o-transfo"
			+ "rm:scale(1.3);transform:scale(1.3)}.item-text{display:inline-block;vertical-align:top;width:430px;ma"
			+ "rgin:0;padding:15px;text-decoration:none;font-size:24px;word-break:break-word;line-height:1.2;-webki"
			+ "t-transition:color .4s;-moz-transition:color .4s;-ms-transition:color .4s;-o-transition:color .4s;tr"
			+ "ansition:color .4s}.item.completed .item-text{color:#a9a9a9;text-decoration:line-through}.item.editi"
			+ "ng .item-text{width:450px;margin-left:53px}.clearfix:after{content:'.';display:block;clear:both;visi"
			+ "bility:hidden;line-height:0;height:0}.clearfix{display:inline-block}html[xmlns] .clearfix{display:bl"
			+ "ock}* html .clearfix{height:1%}";

}
