<%@ page contentType="text/html; charset=euc-kr" %>
<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<!doctype html>
<html>
<head>
	<meta charset="euc-kr">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<base href="http://localhost:8080/cosmarter/">
	<title> CoSmarter </title>
	
	<!-- Bootstrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/kfonts2.css" rel="stylesheet">
	<style>
		body {
			padding-top: 55px;
		}
	</style>
</head>
<body>
	
	<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:alert('TAIN Inc.');">TAIN</a>
			</div>
			
			<!-- collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">POS51<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li role="presentation" class="dropdown-header">--- [ 01. Moniter ] ---</li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/moniter/01.html">01-01. Sample(ps -ef)</a></li>
							<li role="presentation"><a href="javascript:void(0)" onclick="alert('hello2')">JSON 01-2</a></li>
							<li role="presentation"><a href="javascript:void(0)" onclick="alert('hello3')">JSON 01-3</a></li>
							<li role="presentation"><a href="javascript:void(0)" onclick="alert('hello4')">JSON 01-4</a></li>
							<li role="separator" class="divider"></li>
							<li role="presentation"><a href="#">�޴� 4-1</a></li>
						</ul>
					</li>
					<li><a href="#">�޴�2</a></li>
					<li><a href="#">�޴�3</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">����<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li role="presentation" class="dropdown-header">== �����01 ==</li>
							<li role="presentation"><a target="iframeA" href="/html/00.txt">00-00. ����</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/01.html">01-01. ��ư ���</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/02.html">01-02. �ֿ���</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/03.html">01-03. �ֿ���</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/04.html">01-04. �ֿ��� ����͸�</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/05.html">01-05. �ֱ��� ����</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/06.html">01-06. SIGNAL LOG ��Ǫ����</a></li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/07.html">01-07. UMS SIGNAL</a></li>
							<li role="presentation"><a target="iframeA" href="html/ajax/sample/08.html">01-08. �ѱ��׽�Ʈ</a></li>
							<li role="separator" class="divider"></li>
							<li role="presentation" class="dropdown-header">== ����10 ==</li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/R1.html">Run-01. PostNet ����</a></li>
							<li role="presentation" class="dropdown-header">== ����11 ==</li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/R2.html">Run-02. fepstat.sh ����</a></li>
							<li role="presentation" class="dropdown-header">== ����12 ==</li>
							<li role="presentation"><a target="iframeA" href="/html/ajax/sample/R3.html">Run-03. test.sh ����</a></li>
                                                        <li role="presentation" class="dropdown-header">== ����13 ==</li>
                                                        <li role="presentation"><a target="iframeA" href="/html/ajax/sample/R4.html">Run-04. test1.sh ����</a></li>
							<!-- li role="presentation"><a href="#">�޴� 4-1</a></li -->
						</ul>
					</li>
				</ul>
				
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="�˻�">
					</div>
					<button type="submit" class="btn btn-default">SUBMIT</button>
				</form>
			</div>
		</nav>
	</div>
	
	
	<iframe src="about:blank" style="width: 100%; height: 870px; border: none;" name="iframeA"></iframe>
	
	
	
	
	<div> <!-- end of container -->
		<footer style="text-align:center;">
			&copy; 2014~2016 TAIN, Inc. All rights reserved.
		</footer>
		<script src="js/jquery-2.2.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script>
			$(document).ready(function() {
				if (!true) alert('Hello');
			});
		</script>
	</div>
</body>
</html>
