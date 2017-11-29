<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");

	if($_SERVER['REQUEST_METHOD']=="POST"){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idDependencia'])&&isset($_POST['busqueda'])){			
			$sql="SELECT * FROM id3295737_rebuc.vw_usuarios WHERE (idDependencia=".$_POST['idDependencia'].") AND (correo LIKE '%".$_POST['busqueda']."%') AND (rol=3 OR rol=2) ORDER BY idUsuario DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if(isset($_POST['idDependencia'])&&isset($_POST['isSpinner'])){
			$sql="SELECT idUsuario, concat(nombre,' ',apellido) as nombreCompleto FROM id3295737_rebuc.vw_usuarios WHERE idDependencia=".$_POST['idDependencia']." AND (rol=3 OR rol=2) ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);		

		}else if (isset($_POST['idDependencia'])&&isset($_POST['admin'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_usuarios  ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);	

		}else if (isset($_POST['getSolicitantes'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_usuarios WHERE rol=3 ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);	

		}else if(isset($_POST['getBibliotecarios'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_usuarios WHERE rol=2 OR rol=4 ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
		
		}else if (isset($_POST['idDependencia'])){
			
			$sql="SELECT * FROM id3295737_rebuc.vw_usuarios WHERE idDependencia=".$_POST['idDependencia']." AND (rol=3 OR rol=2) ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);			
		}
		$conn->closeConection();

	}

?>