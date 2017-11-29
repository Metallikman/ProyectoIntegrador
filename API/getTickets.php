<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");

	if($_SERVER['REQUEST_METHOD']=="POST"){
		$conn=new conectionSQL();
		$conn->startConection();
		if (isset($_POST['busqueda'])&&isset($_POST['getAllReports'])) {
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE peticion LIKE '%".$_POST['busqueda']."%' ORDER BY fechaAlta DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['busqueda']) && isset($_POST['idDependencia'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE dependenciaSolicitante=".$_POST['idDependencia']." AND peticion LIKE '%".$_POST['busqueda']."%' ORDER BY fechaA
			lta DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['idUsuario'])){					
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE idSolicitante=".$_POST['idUsuario']." ORDER BY fechaAlta DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
			
		}else if (isset($_POST['idDependencia'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE dependenciaSolicitante=".$_POST['idDependencia']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if(isset($_POST['getAllReports'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);	

		}else if (isset($_POST['busqueda'])) {
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE peticion LIKE '%".$_POST['busqueda']."%' ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
		}

		$conn->closeConection();

	}

?>