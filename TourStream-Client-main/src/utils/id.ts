export function genNumId(): number {
  const timestamp = Date.now();
  const randomFactor = Math.floor(Math.random() * 10000);
  const uniqueId = timestamp + randomFactor;

  return uniqueId;
}
