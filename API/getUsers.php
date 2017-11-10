<?php
	include ("controladorConexionMySql.php");

	if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idDependencia'])&&isset($_POST['busqueda'])){	
		
			$sql="SELECT * FROM rebuc.vw_usuarios WHERE (idDependencia=".$_POST['idDependencia'].") AND correo LIKE '%".$_POST['busqueda']."%' ORDER BY idUsuario DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if (isset($_POST['allUsers'])){
			//echo '[{"error":"Usuario y correo no indicado"}]';
			$sql="SELECT * FROM rebuc.vw_usuarios  ORDER BY idUsuario DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);			
		}

		$conn->closeConection();

	}

?>