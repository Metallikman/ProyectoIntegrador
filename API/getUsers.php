<?php
	include ("controladorConexionMySql.php");

	if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idDependencia'])&&isset($_POST['busqueda'])){	
		
			$sql="SELECT * FROM rebuc.vw_usuarios WHERE (idDependencia=".$_POST['idDependencia'].") AND (correo LIKE '%".$_POST['busqueda']."%') AND (rol=3 OR rol=2) ORDER BY idUsuario DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if (isset($_POST['idDependencia'])){
			//echo '[{"error":"Usuario y correo no indicado"}]';
			$sql="SELECT * FROM rebuc.vw_usuarios WHERE idDependencia=".$_POST['idDependencia']." AND (rol=3 OR rol=2) ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);			
		}

		$conn->closeConection();

	}

?>