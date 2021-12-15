export function _filter(value: string, listObj: any, key?: string): string[] {
  const filterValue = value.toLowerCase();
  if (typeof key === 'undefined') {
    return listObj.filter((option: any) => option.toLowerCase().includes(filterValue));
  }
  return listObj.filter((option: any) => option[key].toLowerCase().includes(filterValue));
}
