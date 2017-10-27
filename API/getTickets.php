<?php
	include ("controladorConexionMySql.php");

	//if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idUsuario'])){
			$idUsuario=$_POST['idUsuario'];
			if($idUsuario!=""){
				$sql="SELECT * FROM rebuc.vw_tickets WHERE idSolicitante=".$idUsuario." ORDER BY fechaAlta DESC;";
				$res=$conn->select($sql);
				$outp = array();
				$outp = $res->fetch_all(MYSQLI_ASSOC);
				echo json_encode($outp);
			}else{
				echo '[{"error":"Usuario no indicado"}]';
			}			
		}else{
			$sql="SELECT * FROM rebuc.vw_tickets ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);			
		}

		$conn->closeConection();

	//}

?>