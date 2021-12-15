export function setToArray(set: any): any[] {
  const outputArray: any[] = [];
  set.forEach((element: any) => {
    outputArray.push(element);
  });
  return outputArray;
}
