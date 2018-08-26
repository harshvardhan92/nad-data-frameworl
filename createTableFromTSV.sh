#/bin/bash

#sed 's/,/\n/g' ~/techm/qualityparams/qualitygate.header | xargs -I {} echo {}" string,"

if [ $# -ne 3 ]; then
  echo "Required tsv file, tableName, delimiter"
  exit 1;
fi

tsvFile=$1
tableName=$2
delimiter=$3

cat $tsvFile | head -1 > /tmp/header
sed 's/, /,/g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/ /_/g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/\%//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/\///g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/\.//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/(//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/)//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/_ / /g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/__/_/g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/#//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header
sed 's/-//g' /tmp/header > /tmp/header1
mv /tmp/header1 /tmp/header

echo "CREATE TABLE $tableName ("
sed 's/'$delimiter'/\n/g' /tmp/header | xargs -I {} echo {}" string,"
echo ") row format delimited fields terminated by '$delimiter';"

