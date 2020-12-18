csar_file_path=$1
result_json_path=$2

ls $csar_file_path
if [ $? -ne 0 ]; then
  echo "provide csar file path"
  exit 1
fi

Append_String="{"

#compliance-check-1
Append_String=$Append_String"\n \"compliance-check-1\": "
output_json=$(ocomp --product EdgeGallery compliance-check-1 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-2
Append_String=$Append_String"\n \"compliance-check-2\": "
output_json=$(ocomp --product EdgeGallery compliance-check-2 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-3
Append_String=$Append_String"\n \"compliance-check-3\": "
output_json=$(ocomp --product EdgeGallery compliance-check-3 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-4
Append_String=$Append_String"\n \"compliance-check-4\": "
output_json=$(ocomp --product EdgeGallery compliance-check-4 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json

Append_String=$Append_String"}"

echo $Append_String > $result_json_path
