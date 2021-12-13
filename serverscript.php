public void storeVideo(Request $request){
    move_uploaded_file($_FILES["myFile"]["tmp_name"], $_FILES["myFile"]["name"]);
      return $_FILES["myFile"]["name"];
}
public void storeImage(Request $request){
    $file_data = $request->input('media');
    $file_name = 'image_' . time() . '.mp4'; //generating unique file name;
    file_put_contents($file_name,base64_decode($file_data));
}


<!-- 
public function challanverify(Request $request ){

//  move_uploaded_file($_FILES["myFile"]["tmp_name"], $_FILES["myFile"]["name"]);

//    echo $request->role;
 // echo $_FILES["myFile"]["name"] ;

  return json_encode($_POST['role ']);

  $Role = $request->Role;
  $action = $request->action;
  $challanid = $request->challanid;

  $file_data = $request->input('media');
  $file_name = 'image_' . time() . '.mp4'; //generating unique file name; -->